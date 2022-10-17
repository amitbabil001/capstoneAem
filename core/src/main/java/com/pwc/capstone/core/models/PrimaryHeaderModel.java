package com.pwc.capstone.core.models;

import java.util.Date;
import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.pwc.capstone.core.utils.CapstoneClassUtils;

@Model (adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PrimaryHeaderModel {
    
    @ValueMapValue
    private String trendingText;
    
    @ValueMapValue
    private String trendingDesc;
    
    private String dateStr;
    
    @PostConstruct
    public void init() {
        dateStr = CapstoneClassUtils.getDate(new Date());
    }

    public String getTrendingText() {
        return trendingText;
    }

    public String getTrendingDesc() {
        return trendingDesc;
    }

    public String getDateStr() {
        return dateStr;
    }
    
    
}
