package com.example.daytwo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entry {
    
    /**
     * promptID
     * weather
     *   moonPhaseCode
     *   sunsetDate
     *   weatherServiceName
     *   weatherCode
     *   temperatureCelsius (float)
     *   windBearing (int)
     *   sunriseDate
     *   conditionsDescription
     *   pressureMB (int)
     *   moonPhase (float)
     *   visibilityKM (float)
     *   relativeHumidity (int)
     *   windSpeedKPH (int)
     *   windChillCelsius (float)
     * location
     *   region
     *     center
     *       longitude (float)
     *       latitude (float)
     *     radius (int)
     *   localityName
     *   country
     *   timeZoneName
     *   administrativeArea
     *   longitude (float)
     *   placeName
     *   latitude (float)
     */
    
    @Id
    String uuid;
    
    String modifiedDate;
    String creationDate;
    
    @Transient
    List<String> tags;
    String tagString;
    String text;
    String richText;
    
    String creationDevice;
    String creationDeviceModel;
    String creationDeviceType;
    String creationOSName;
    String creationOSVersion;
    boolean starred = false;
    float editingTime;
    int duration;
    String timeZone;

    static List<String> searchableFields;

    static {
        searchableFields = new ArrayList<>();
        searchableFields.add("creationDevice");
    }

    public Entry stringifyTags() {
        if (tags.size() > 0) {
            tagString = "";
            for (String tag : tags) {
                tagString += tag + "|";
            }
            tagString = tagString.substring(0, tagString.length() - 1);
        }
        return this;
    }
    
    public Entry listifyTags() {
        if (tagString != null) {
            String[] tagArray = tagString.split("\\|");
            if (tags == null) tags = new LinkedList<>();
            for (String tag : tagArray) {
                if (!tags.contains(tag)) {
                    tags.add(tag);
                }
            }
        }
        return this;
    }

    public boolean addTag(String tag) {
        if (this.tags.add(tag)) {
            stringifyTags();
            return true;
        }
        return false;
    }

    public boolean removeTag(String tag) {
        if (this.tags.remove(tag)) {
            stringifyTags();
            return true;
        }
        return false;
    }

    public boolean replaceTag(String tag, String with) {
        if (removeTag(tag)) {
            return addTag(with);
        }
        return false;
    }

    public boolean containsTag(String tag) {
        return this.tags.contains(tag);
    }
}
