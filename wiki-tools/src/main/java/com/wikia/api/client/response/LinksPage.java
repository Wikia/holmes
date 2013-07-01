package com.wikia.api.client.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Artur Dwornik
 * Date: 29.06.13
 * Time: 17:33
 */

public class LinksPage implements Serializable {
    private static final long serialVersionUID = -4343138799883949932L;
    @SerializedName("pageid")
    private Long id;
    private String title;
    @SerializedName("ns")
    private Long namespace;
    private List<LinksLink> links = new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getNamespace() {
        return namespace;
    }

    public void setNamespace(Long namespace) {
        this.namespace = namespace;
    }

    public List<LinksLink> getLinks() {
        return links;
    }

    public void setLinks(List<LinksLink> links) {
        this.links = links;
    }
}


/* example;
{
      "pageid": 736,
      "ns": 0,
      "title": "Albert Einstein",
      "links": [
          {
              "ns": 0,
              "title": "A priori and a posteriori"
          },
          {
              "ns": 0,
              "title": "Aarau"
          },
          {
              "ns": 0,
              "title": "Abba Eban"
          },
          {
              "ns": 0,
              "title": "Abdominal aortic aneurysm"
          },
          {
              "ns": 0,
              "title": "Abraham Pais"
          },
          {
              "ns": 0,
              "title": "Absent-minded professor"
          },
          {
              "ns": 0,
              "title": "Absorption refrigerator"
          },
          {
              "ns": 0,
              "title": "Action-angle variables"
          },
          {
              "ns": 0,
              "title": "Adiabatic invariant"
          },
          {
              "ns": 0,
              "title": "Adolf Gr\u00fcnbaum"
          }
      ]
}
*/