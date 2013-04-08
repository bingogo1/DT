package bin.g11n.gt.web.taglib.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import org.springframework.web.util.ExpressionEvaluationUtils;

import bin.g11n.gt.security.cache.FunctionsByUserCache;

/**
 * An implementation of {@link javax.servlet.jsp.tagext.Tag} that allows it's
 * body through if some authorizations are granted to the request's principal.
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class AuthorizeActionTag extends RequestContextAwareTag {
	// ~ Instance fields
	// ================================================================================================

	private String ifAllGranted = "";

	private String ifAnyGranted = "";

	private String ifNotGranted = "";

	// ~ Methods
	// ========================================================================================================
	/**
	 * get function set from granted authority (function objects)
	 * 
	 * functionCollection
	 * 
	 * @param functionCollection
	 *            role collection
	 * @return Set role name set
	 */
	private Set authoritiesToFunctions(Collection functionCollection) {
		Set targetFunction = new HashSet();

		for (Iterator iterator = functionCollection.iterator(); iterator
				.hasNext();) {
			GrantedAuthority function = (GrantedAuthority) iterator.next();

			if (null == function.getAuthority()) {
				throw new IllegalArgumentException(
						"Cannot process GrantedAuthority objects which return null from getAuthority() - attempting to process "
								+ function.toString());
			}
			// add function set of this role
			targetFunction.add(function.getAuthority());
		}

		return targetFunction;
	}

	/**
	 * filter unauthorized objects.
	 * 
	 * doStartTagInternal 
	 *
	 * @return
	 * @throws JspException  int
	 */
	public int doStartTagInternal() throws JspException {
		if (((null == ifAllGranted) || "".equals(ifAllGranted))
				&& ((null == ifAnyGranted) || "".equals(ifAnyGranted))
				&& ((null == ifNotGranted) || "".equals(ifNotGranted))) {
			// if the user is anonymous user, skip content
			Authentication currentUser = SecurityContextHolder.getContext()
					.getAuthentication();
			if (null == currentUser
					|| " anonymous".equals(currentUser.getPrincipal())) {
				return Tag.SKIP_BODY;
			}
			// if parameter has not set in the tag, when using the
			// <authz:function></authz:function>, display the content of this
			// tag.
			return Tag.EVAL_BODY_INCLUDE;
		}
		// grants functions in the z_r_role_function of the current user
		List<String> grantedFunctionNameList = getGrantedFunctions();

		String evaledIfNotGranted = ExpressionEvaluationUtils.evaluateString(
				"ifNotGranted", ifNotGranted, pageContext);

		if ((null != evaledIfNotGranted) && !"".equals(evaledIfNotGranted)) {
			grantedFunctionNameList
					.retainAll(parsefunctionNamesString(evaledIfNotGranted));
			if (!grantedFunctionNameList.isEmpty()) {
				return Tag.SKIP_BODY;
			}
		}

		String evaledIfAllGranted = ExpressionEvaluationUtils.evaluateString(
				"ifAllGranted", ifAllGranted, pageContext);

		if ((null != evaledIfAllGranted) && !"".equals(evaledIfAllGranted)) {
			List<String> requiredFunctionNameList = parsefunctionNamesString(evaledIfAllGranted);
			grantedFunctionNameList.retainAll(requiredFunctionNameList);
			if (grantedFunctionNameList.size() != requiredFunctionNameList
					.size()) {
				return Tag.SKIP_BODY;
			}

		}

		final String evaledIfAnyGranted = ExpressionEvaluationUtils
				.evaluateString("ifAnyGranted", ifAnyGranted, pageContext);

		if ((null != evaledIfAnyGranted) && !"".equals(evaledIfAnyGranted)) {
			grantedFunctionNameList
					.retainAll(parsefunctionNamesString(evaledIfAnyGranted));
			if (grantedFunctionNameList.isEmpty()) {
				return Tag.SKIP_BODY;
			}
		}

		return Tag.EVAL_BODY_INCLUDE;
	}

	/**
	 * get granted authorities( here are function names ) of the current user
	 * 
	 * getPrincipalFunctions
	 * 
	 * @return List<String> function name list
	 */
	private List<String> getGrantedFunctions() {
		List<String> grantedFunctionNameList = new ArrayList<String>();
		Authentication currentUser = SecurityContextHolder.getContext()
				.getAuthentication();
		if (null == currentUser) {
			return Collections.EMPTY_LIST;
		}
		if ((null == currentUser.getAuthorities())
				|| (currentUser.getAuthorities().length < 1)) {
			return Collections.EMPTY_LIST;
		}
		GrantedAuthority[] grantedFunctions = getFunctionsByUserCache()
				.getFunctionFromCache(currentUser.getName());
		if (grantedFunctions != null && grantedFunctions.length > 0) {
			for (int i = 0; i < grantedFunctions.length; i++) {
				grantedFunctionNameList.add(grantedFunctions[i].getAuthority());
			}
		}
		return grantedFunctionNameList;
	}

	/**
	 * get function name list from the parameters of tag.
	 * 
	 * functionsString function names delimited with comma
	 * 
	 * @param authorizationsString
	 *            functionnames delimited with comma.
	 * @return List<String>
	 */
	private List<String> parsefunctionNamesString(String functionsString) {
		final List<String> requiredAuthorities = new ArrayList<String>();
		final String[] authorities = StringUtils
				.commaDelimitedListToStringArray(functionsString);

		for (int i = 0; i < authorities.length; i++) {
			String authority = authorities[i];

			// Remove the function's whitespace characters without depending on
			// JDK 1.4+
			// Includes space, tab, new line, carriage return and form feed.
			String functionName = authority.trim(); // trim, don't use spaces,
													// as per SEC-378
			functionName = StringUtils.replace(functionName, "\t", "");
			functionName = StringUtils.replace(functionName, "\r", "");
			functionName = StringUtils.replace(functionName, "\n", "");
			functionName = StringUtils.replace(functionName, "\f", "");

			requiredAuthorities.add(functionName);
		}

		return requiredAuthorities;
	}

	/**
	 * Find the common authorities between the current authentication's
	 * 
	 * @param grantedFunctionNameCollection
	 *            granted function name collection
	 * @param requiredFunctionSet
	 *            A {@link Set} of {@link GrantedAuthorityImpl}s that have been
	 *            built using ifAny, ifAll or ifNotGranted.
	 * 
	 * @return A set containing only the common authorities between <var>granted</var>
	 *         and <var>required</var>.
	 * 
	 * @see <a
	 *      href="http://forum.springframework.org/viewtopic.php?t=3367">authz:authorize
	 *      ifNotGranted not behaving as expected</a>
	 */
	private Set retainAll(final Collection grantedFunctionNameCollection,
			final Set requiredFunctionSet) {
		Set grantedFunctionNames = authoritiesToFunctions(grantedFunctionNameCollection);
		Set requiredRoles = authoritiesToFunctions(requiredFunctionSet);
		// get the common collection of the granted and requiered function
		grantedFunctionNames.retainAll(requiredRoles);

		return functionsToAuthorities(grantedFunctionNames,
				grantedFunctionNameCollection);
	}

	/**
	 * functionsToAuthorities
	 * 
	 * @param grantedFunctions
	 * @param granted
	 * @return Set
	 */
	private Set functionsToAuthorities(Set grantedFunctions,
			Collection grantedRoleCollection) {
		Set target = new HashSet();

		for (Iterator iterator = grantedFunctions.iterator(); iterator
				.hasNext();) {
			String functionName = (String) iterator.next();

			for (Iterator grantedIterator = grantedRoleCollection.iterator(); grantedIterator
					.hasNext();) {
				GrantedAuthority authority = (GrantedAuthority) grantedIterator
						.next();

				if (authority.getAuthority().equals(functionName)) {
					target.add(authority);

					break;
				}
			}
		}

		return target;
	}

	// getter and setter
	public void setIfAllGranted(String ifAllGranted) throws JspException {
		this.ifAllGranted = ifAllGranted;
	}

	public void setIfAnyGranted(String ifAnyGranted) throws JspException {
		this.ifAnyGranted = ifAnyGranted;
	}

	public void setIfNotGranted(String ifNotGranted) throws JspException {
		this.ifNotGranted = ifNotGranted;
	}

	public String getIfAllGranted() {
		return ifAllGranted;
	}

	public String getIfAnyGranted() {
		return ifAnyGranted;
	}

	public String getIfNotGranted() {
		return ifNotGranted;
	}

	/**
	 * the Getter of functionsByUserCache
	 * 
	 * @return Returns the functionsByUserCache.
	 */
	public FunctionsByUserCache getFunctionsByUserCache() {
		return (FunctionsByUserCache) this.getRequestContext()
				.getWebApplicationContext().getBean("functionsUserCache");
	}

}
