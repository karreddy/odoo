<nav aria-label="<@liferay.language key="site-pages" />" class="${nav_css_class} navbar navbar-expand-lg navbar-light bg-transparent mahaCyberNavTitle" id="navigation" role="navigation"> 
	<a class="${logo_css_class} navbar-brand" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
		<img alt="${logo_description}" src="${site_logo}" style="width:70px;"/>
	</a> 	

	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#mahaCyberNav" aria-controls="mahaCyberNav" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="mahaCyberNav">
		<ul role="menubar" class="navbar-nav ml-auto">
			<#list nav_items as nav_item>
				<#assign
						nav_item_attr_has_popup = ""
						nav_item_attr_selected = ""
						nav_item_css_class = ""
						nav_item_dropdown = ""
						nav_a_css_class = ""
						nav_a_dropdown_toggle = ""
						nav_span_dropdown_toggle = ""
						nav_item_layout = nav_item.getLayout()
					/>
					
					<#assign nav_item_layout = nav_item.getLayout() />

					<#if nav_item.isSelected()>
						<#assign
							nav_item_attr_has_popup = "aria-haspopup='true'"
							nav_item_attr_selected = "aria-selected='true'"
							nav_item_css_class = "selected active"
						/>
					</#if>
					
					<#if nav_item.hasChildren()>
						<#assign
							nav_item_dropdown = "dropdown"
							nav_a_css_class = "dropdown-toggle"
							nav_a_dropdown_toggle = "aria-expanded='false'"
							nav_span_dropdown_toggle = "class='bi bi-chevron-down arrow-right'"
						/>
					</#if>

				<li class="${nav_item_css_class} ${nav_item_dropdown} nav-item" id="layout_${nav_item.getLayoutId()}" role="presentation"> 
					<a ${nav_item_attr_has_popup} href="${nav_item.getURL()}" ${nav_item.getTarget()} role="menuitem" class="nav-link main-nav">
						<span>
							<@liferay_theme["layout-icon"] layout=nav_item_layout /> ${nav_item.getName()}
						</span>
					</a>

					<#if nav_item.hasChildren()>
						<ul class="child-menu dropdown-menu nav-item" role="menu">
							<#list nav_item.getChildren() as nav_child>
								<#assign
									nav_child_css_class = ""
									nav_child_attr_selected = ""
								/>

								<#if nav_item.isSelected()>
									<#assign
										nav_child_css_class = "selected active"
									/>
								</#if>

								<li class="${nav_child_css_class} nav-item" id="layout_${nav_child.getLayoutId()}" role="presentation">
									<a href="${nav_child.getURL()}" ${nav_child.getTarget()} role="menuitem">${nav_child.getName()}</a>
								</li>
							</#list>
						</ul>
					</#if>
				</li>
			</#list>
			<li class="nav-item sign-in">
				<a href="/login">
					<i class="bi bi-person-lock mr-3"></i>Sign In 
				</a>				
			</li>
		</ul>
	</div>
</nav>





