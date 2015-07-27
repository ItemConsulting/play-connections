package no.item.play.connections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface Constants {
    String PATH_CONNECTIONS_HOMEPAGE = "homepage";
    String PATH_CONNECTIONS_BLOGS = "blogs";
    String PATH_CONNECTIONS_ACTIVITIES = "activities";

    Map<String, String> NAMESPACES = Collections.unmodifiableMap(new HashMap<String, String>() {{
        put("a", "http://www.w3.org/2005/Atom");
        put("td", "urn:ibm.com/td");
        put("ca", "http://www.ibm.com/xmlns/prod/composite-applications/v1.0");
        put("snx", "http://www.ibm.com/xmlns/prod/sn");
        put("type", "http://www.ibm.com/xmlns/prod/sn/type");
        put("source", "http://www.ibm.com/xmlns/prod/sn/source");
        put("source", "http://www.ibm.com/xmlns/prod/sn/resource-type");
        put("source", "http://www.ibm.com/xmlns/prod/sn/resource-id");
        put("container", "http://www.ibm.com/xmlns/prod/sn/container");
        put("priority", "http://www.ibm.com/xmlns/prod/sn/priority");
        put("flags", "http://www.ibm.com/xmlns/prod/sn/flags");
        put("connection", "http://www.ibm.com/xmlns/prod/sn/connection/type");
        put("status", "http://www.ibm.com/xmlns/prod/sn/status");
        put("thr", "http://purl.org/syndication/thread/1.0");
        put("fh", "http://purl.org/syndication/history/1.0");
        put("opensearch", "http://a9.com/-/spec/opensearch/1.1/");
        put("app", "http://www.w3.org/2007/app");
        put("relevance", "http://a9.com/-/opensearch/extensions/relevance/1.0/");
        put("ibmsc", "http://www.ibm.com/search/content/2010");
        put("xs", "http://www.w3.org/2001/XMLSchema");
        put("xhtml", "http://www.w3.org/1999/xhtml");
        put("h", "http://www.w3.org/1999/xhtml");
        put("cmisra", "http://docs.oasis-open.org/ns/cmis/restatom/200908/");
        put("cmism", "http://docs.oasis-open.org/ns/cmis/messaging/200908/");
        put("lcmis", "http://www.ibm.com/xmlns/prod/sn/cmis");
        put("cmis", "http://docs.oasis-open.org/ns/cmis/core/200908/");
        put("opensocial", "http://ns.opensocial.org/2008/opensocial");
        put("tag", "tag:ibm.com,2006:td/type");
        put("tag", "tag:profiles.ibm.com,2006:entry");
        put("scheme", "http://www.ibm.com/xmlns/prod/sn/type");
        put("xmlns", "http://www.w3.org/2000/xmlns/");
        put("rel", "http://www.ibm.com/xmlns/prod/sn/parentcommunity");
        put("component", "http://www.ibm.com/xmlns/prod/sn/communities");
    }});
}

