package com.pwc.capstone.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * @author Amit_Babil
 *
 */
@Model (adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BannerArticleModel {

    @ValueMapValue
    private String bannerImage;
    
    @ValueMapValue
    private String bannerText;
    
    private String pageCreatedDate;

    public String getBannerImage() {
        return bannerImage;
    }

    public String getBannerText() {
        return bannerText;
    }

    public String getPageCreatedDate() {
        return pageCreatedDate;
    }

    /**
     * @param createdDateStr
     */
    public void setPageCreatedDate(String createdDateStr) {
        this.pageCreatedDate=createdDateStr;
        
    }
    
    
}
