package com.pwc.capstone.core.models;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VideoPlaylistModel {

    @ValueMapValue
    private String[] youTubeLinks;

    List<String> videoplay;

    private static final Logger LOG = LoggerFactory.getLogger(VideoPlaylistModel.class);

    /**
     * this init() method helps to fetch the url from provided youtube links
     * this links assign in the properties used in dailog
     */
    @PostConstruct
    public void init() {
        if (youTubeLinks != null && youTubeLinks.length > 0) {
            videoplay = new ArrayList<>();
            try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
                for (String url : youTubeLinks) {
                      String videoResponce =getYoutubeResponce(httpClient, url);
                      if(videoResponce != null) {
                          videoplay.add(videoResponce);
                      }
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    
    public String getYoutubeResponce(CloseableHttpClient httpClient, String url) {
        HttpGet request = new HttpGet("https://www.youtube.com/oembed?url=" + url);
        try(CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {                           
                String result = EntityUtils.toString(entity);
                try(JsonReader jsonReader = Json.createReader(new StringReader(result))){
                    JsonObject youtubeResponse = jsonReader.readObject();
                    return  youtubeResponse.getString("html");
                }
            }
        } catch (ClientProtocolException e) {
            LOG.debug("failed to get responce of youtube links");
        } catch (IOException e) {
           LOG.debug("failed to get responce of youtube links");
        }
        return null;
        
    }
    
    public List<String> getVideoplay() {
        return videoplay;
    }

    
}
