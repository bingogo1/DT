<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="WEB-INF/classes/cssHorizontalMenu.vm" permissions="rolesAdapter">
<ul id="primary-nav" class="menuList">
        <!-- menu : displayMenu name="MainMenu"/ -->
    <authz:function ifAnyGranted="productMgmt.action">
        <menu:displayMenu name="ProductMgmtMenu"/>
    </authz:function>
    <authz:function ifNotGranted="productMgmt.action">
      <authz:function ifAnyGranted="productCenterMgmt.action">
        <menu:displayMenu name="ProductCentMgmtMenu"/>
      </authz:function>
      <authz:function ifNotGranted="productCenterMgmt.action">
        <authz:function ifAnyGranted="productLineMgmt.action">
          <menu:displayMenu name="PLineMgmtMenu"/>
        </authz:function>
      </authz:function>
    </authz:function>
    
    <authz:function ifAnyGranted="userMgmt.action">
      <menu:displayMenu name="UserMgmtMenu"/>
    </authz:function>
    <authz:function ifNotGranted="userMgmt.action">
      <authz:function ifAnyGranted="editProfile.action">
        <menu:displayMenu name="UserMenu"/>
      </authz:function>
    </authz:function>
    
</ul>
</menu:useMenuDisplayer>
