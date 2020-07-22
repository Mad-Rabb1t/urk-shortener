package app.controller;


import app.service.RequestService;
import app.service.UrlsService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
@RequestMapping("/sh")
public class ShortenController {

    private final UrlsService service;
    private final RequestService reqService;

    public ShortenController(UrlsService service, RequestService reqService) {
        this.service = service;
        this.reqService = reqService;
    }


    @GetMapping(path = "{url_id}")
    public RedirectView handler(@PathVariable String url_id, HttpServletRequest request) {
        //Firstly, we are looking for the data with the passed short url in db, if it exists we redirect the user to
        // that url if not user is redirected to an error page.

        if (service.doesExistWithThisShortUrl(url_id)) {
            String ipAddress = ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getDetails()).getRemoteAddress();
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            String browser_info = String.format("%s, %s", userAgent.getBrowser().getName(),
                    userAgent.getBrowserVersion().getVersion());
            String os = userAgent.getOperatingSystem().getName();

            //Extracting data from json request from api
            String lat_lon, city_country, org, mob_net = "⚫ accessed via mobile network ⚫";
            try {
                JSONObject jsonObject = new JSONObject(reqService.getIpDetails(ipAddress));
                if (jsonObject.getString("status").trim().equals("success")) {
                    city_country = String.format("%s, %s", jsonObject.getString("city"), jsonObject.getString("country"));
                    lat_lon = String.format("%s, %s", jsonObject.getFloat("lat"), jsonObject.getFloat("lon"));
                    org = String.format("%s %s", jsonObject.getString("org"),
                            jsonObject.getBoolean("mobile") ? mob_net : "");
                } else {
                    city_country = "Unknown";
                    lat_lon = "Unknown";
                    org = "Unknown";
                }
            } catch (Exception ex) {
                log.warn("Exception occured with JSON handling --->" + ex);
                city_country = "Unknown";
                lat_lon = "Unknown";
                org = "Unknown";
            }

            service.createVisitDetails(url_id, ipAddress, browser_info, os, lat_lon, city_country, org);
            return new RedirectView(service.getFullUrl(url_id));
        } else return new RedirectView("/error/not-found");

    }
}
