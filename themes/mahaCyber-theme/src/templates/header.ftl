<div class="header d-md-block d-none d-sm-none">
    <div class="row mahaCyberNavHead">
        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-xs-12">
            <div class="social-media-icons">
                <ul class="list-inline mb-0 p-3"> 
                    <li class="list-inline-item mr-4">
                        <a href="https://www.facebook.com/MahaCyber/" target="_blank" class="text-white text-decoration-none" title="Facebook">
                            <i class="bi bi-facebook text-5"></i>
                        </a>                        
                    </li>

                    <li class="list-inline-item mr-4">
                        <a href="https://www.instagram.com/mahacyber/?hl=en" target="_blank" class="text-white text-decoration-none" title="Instagram">
                            <i class="bi bi-instagram text-5"></i>
                        </a>                        
                    </li>

                    <li class="list-inline-item mr-4">
                        <a href="https://twitter.com/MahaCyber1?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor" target="_blank" class="text-white text-decoration-none" title="Twitter">
                            <i class="bi bi-twitter-x text-5"></i>
                        </a>                        
                    </li>

                    <li class="list-inline-item">
                        <a href="https://www.youtube.com/channel/UC4S7a5Sc1Tn3ta6OkrB6RKA" target="_blank" class="text-white text-decoration-none" title="Youtube">
                            <i class="bi bi-youtube text-5"></i>
                        </a>                        
                    </li> 
                </ul>
            </div>
        </div>

        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-xs-12">
            <div class="other-links text-right">
                <ul class="list-inline mb-0 pt-2">
                    <li class="list-inline-item">
                        <@liferay_portlet["runtime"]
                            portletProviderAction=portletProviderAction.VIEW
                            portletProviderClassName="com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry"
                        />
                    </li>    

                    <li class="list-inline-item mr-5">
                        <a href="javascript:;" class="text-decoration-none text-white">
                            Screen Reader
                        </a>
                    </li>

                    <li class="list-inline-item mr-4">
                        <span class="text-decoration-none text-white text-3 pointer" id="incrementFont">A+</span>
                    </li>

                    <li class="list-inline-item mr-4">
                        <span class="text-decoration-none text-white text-3 pointer" id="resetFont">A</span>
                    </li>

                    <li class="list-inline-item mr-4">
                        <span class="text-decoration-none text-white text-3 pointer" id="decrementFont">A-</span>
                    </li> 

                    <li class="list-inline-item pointer" id="darkModeToggle">  
                        <img id="darkModeIcon" src="${images_folder}/icons/darkmode.png" title="Theme Select" style="filter: invert(1);width: 25px;" class="pointer">  
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>