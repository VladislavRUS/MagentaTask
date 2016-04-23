package com.appmagenta.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "distances")
@XmlAccessorType(XmlAccessType.FIELD)
public class DistanceWrapper {
    @XmlElement(name = "distance")
    private List<Distance> distanceList;

    public List<Distance> getDistanceList() {
        return distanceList;
    }

    public void setDistanceList(List<Distance> distanceList) {
        this.distanceList = distanceList;
    }
}
