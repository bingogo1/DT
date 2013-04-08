package bin.g11n.gt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayTagReplacementUtil {
	// Regular expression that should filter out displaying x to y.
	private static final String DASHBOARD_LINK_REGEX = "(\"(replaceURI)\\?([a-zA-Z_0-9-&;=]*)(\"))";
	private static final String DISPLAYING_REGEX = "((displaying)\\s(([0-9]*)\\sto\\s([0-9]*)))";
	private static final String PAGE_LINKS_REGEX = "(<span class=\"pagelinks\">)(.*)(</span>)";
	private static final String A_HREF = "<a href=\"replaceURI?";

	/**
	 * Updates the Paging html. It updates the current displaying range and links,  so that the correct page is selected and the first/next/prev/last links are correctly set.
	 * 
	 * @param pagingHtml The html to update.
	 * @param context The context containing page and maxSize properties
	 * @param numberOfResults The number of results there actually are.
	 * @param displayTagPage The name that displayTag gave the page parameter.
	 * @return html the updated html.
	 */
	public static String updatePagingHtml(String pagingHtml, int selectedPage, int maxResults, int numberOfResults, String displayTagPage) {
		// we need to update the displaying text.
		Matcher matcher = Pattern.compile(DISPLAYING_REGEX).matcher(pagingHtml);
		while (matcher.find()) {
			// we need to know which items we are displaying.
			int startItem = (selectedPage == 1 ? 1 : (selectedPage - 1) * maxResults);
			// the last item can never be more than the number of results there are.
			int temp = (startItem + maxResults - 1);
			int stopItem = temp > numberOfResults ? numberOfResults : temp;

			pagingHtml = pagingHtml.replace(matcher.group(1), matcher.group(2) + " " + startItem + " to " + stopItem);
		}
		// now we need to update the pageLinks.
		matcher = Pattern.compile(PAGE_LINKS_REGEX).matcher(pagingHtml);
		while (matcher.find()) {
			pagingHtml = pagingHtml.replace(matcher.group(2), createPageLinksHtml(selectedPage, maxResults, numberOfResults, displayTagPage));
		}
		return pagingHtml;
	}
	
	 /**
     * Create the paging links html.
     * @param displayTagPage The current page that the table is displaying.
     * @param fetchSize The number of rows that the table can display.
     * @param numberOfResults The number of results that the table contains.
     * @return pageLinks The newly created html that represents pageLinks.
     */
    private static String createPageLinksHtml(int selectedPage, int fetchSize, int numberOfResults, String displayTagPage) {
        String postfix = selectedPage == 1 ? "[First/Prev] " : ("[" + A_HREF + displayTagPage + "=1\">First</a>/" + A_HREF + displayTagPage + "=" + (selectedPage - 1) + "\">Prev</a>" + "] ");
        String links = "";
        int startPage, stopPage = 0;
        int maxPage = Math.round((numberOfResults / fetchSize) + 1);

        // the startPage should never be negative.
        if(selectedPage - 3 < 1) {
            startPage = 1;
            stopPage = startPage + 8;
        } else {
            startPage = selectedPage - 3;
            stopPage = startPage + 8;
        }
        // the stopPage should never be more then the maxPage.
        if(stopPage >= maxPage) {
            stopPage = maxPage+1;
            startPage = stopPage - 8;
        }
            
        for(int i = startPage; i < stopPage; i++) {
            if (startPage != i && stopPage!= maxPage) {
                links += ", ";
            }
            if (selectedPage != i) {
                links += A_HREF + displayTagPage + "=" + i + "\" title=\"Go to page " + i + "\">" + i + "</a>";
            } else {
                links += "<strong>" + selectedPage + "</strong>";
            }
        }
        
        String prefix = selectedPage == maxPage ? " [Next/Last]" : " ["+ A_HREF + displayTagPage + "=" + (selectedPage + 1) + "\">Next</a>/" + A_HREF + displayTagPage + "=" + Math.round((numberOfResults / fetchSize) + 1) + "\">Last</a>]";
        return postfix + links + prefix;
    }
    
    /**
     * Updates the html links so that sortOrder is correct.
     * @param html The html to update.
     * @param sortOrder The current sortOrder.
     * @return html the updated html.
     */
    public static String updateSortOrderHtml(String html, boolean sortOrder, String displayTagSortOrder) {
        Matcher matcher = Pattern.compile("((" + displayTagSortOrder + "=)([0-9]*))").matcher(html);
        while (matcher.find()) {
            html = html.replace(matcher.group(1), matcher.group(2) + (sortOrder ? 2 : 1));
        }
        return html;
    }
    
    /**
     * Updates all HtmlLinks and replaces them with Javascript action calls, so future paging and sorting of this 
     * displaytable will be ajax enabled.
     * @param html The html to update.
     * @param dateFrom The dateFrom.
     * @param dateTo The dateTo.
     * @return html the updated html.
     */
    public static String updateHtmlLinks(String html) {
        Matcher matcher = Pattern.compile(DASHBOARD_LINK_REGEX).matcher(html);
        while (matcher.find()) {
            html = html.replace(matcher.group(1), "javascript:update('" + matcher.group(3) + "')");
        }
        return html;
    }
}
