package com.appmagenta.wrappers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlWrapper {

    @XmlElement(name = "cities")
    private CityWrapper cityWrapper;

    @XmlElement(name = "distances")
    private DistanceWrapper distanceWrapper;

    public CityWrapper getCityWrapper() {
        return cityWrapper;
    }

    public void setCityWrapper(CityWrapper cityWrapper) {
        this.cityWrapper = cityWrapper;
    }

    public DistanceWrapper getDistanceWrapper() {
        return distanceWrapper;
    }

    public void setDistanceWrapper(DistanceWrapper distanceWrapper) {
        this.distanceWrapper = distanceWrapper;
    }
}
