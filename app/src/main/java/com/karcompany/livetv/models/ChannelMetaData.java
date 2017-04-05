/**
 * Created by pvkarthik on 2017-02-15.
 *
 * This is POJO class corresponding to server response (JSON).
 */
package com.karcompany.livetv.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChannelMetaData implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("claim")
    @Expose
    private String claim;
    @SerializedName("shortname")
    @Expose
    private String shortname;
    @SerializedName("videoRelations")
    @Expose
    private List<String> videoRelations = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("providers")
    @Expose
    private List<String> providers = null;
    @SerializedName("channelNumber")
    @Expose
    private String channelNumber;
    @SerializedName("worksheetId")
    @Expose
    private String worksheetId;
    @SerializedName("sort")
    @Expose
    private long sort;
    @SerializedName("NameDeutsch")
    @Expose
    private String nameDeutsch;
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("lastUpdate")
    @Expose
    private long lastUpdate;
    public final static Parcelable.Creator<ChannelMetaData> CREATOR = new Creator<ChannelMetaData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ChannelMetaData createFromParcel(Parcel in) {
            ChannelMetaData instance = new ChannelMetaData();
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.claim = ((String) in.readValue((String.class.getClassLoader())));
            instance.shortname = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.videoRelations, (java.lang.String.class.getClassLoader()));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.displayName = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.images, (com.karcompany.livetv.models.Image.class.getClassLoader()));
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.providers, (java.lang.String.class.getClassLoader()));
            instance.channelNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.worksheetId = ((String) in.readValue((String.class.getClassLoader())));
            instance.sort = ((long) in.readValue((long.class.getClassLoader())));
            instance.nameDeutsch = ((String) in.readValue((String.class.getClassLoader())));
            instance.active = ((boolean) in.readValue((boolean.class.getClassLoader())));
            instance.lastUpdate = ((long) in.readValue((long.class.getClassLoader())));
            return instance;
        }

        public ChannelMetaData[] newArray(int size) {
            return (new ChannelMetaData[size]);
        }

    }
    ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public List<String> getVideoRelations() {
        return videoRelations;
    }

    public void setVideoRelations(List<String> videoRelations) {
        this.videoRelations = videoRelations;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getProviders() {
        return providers;
    }

    public void setProviders(List<String> providers) {
        this.providers = providers;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getWorksheetId() {
        return worksheetId;
    }

    public void setWorksheetId(String worksheetId) {
        this.worksheetId = worksheetId;
    }

    public long getSort() {
        return sort;
    }

    public void setSort(long sort) {
        this.sort = sort;
    }

    public String getNameDeutsch() {
        return nameDeutsch;
    }

    public void setNameDeutsch(String nameDeutsch) {
        this.nameDeutsch = nameDeutsch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(title).append(claim).append(shortname).append(videoRelations).append(description).append(displayName).append(images).append(id).append(providers).append(channelNumber).append(worksheetId).append(sort).append(nameDeutsch).append(active).append(lastUpdate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ChannelMetaData) == false) {
            return false;
        }
        ChannelMetaData rhs = ((ChannelMetaData) other);
        return new EqualsBuilder().append(title, rhs.title).append(claim, rhs.claim).append(shortname, rhs.shortname).append(videoRelations, rhs.videoRelations).append(description, rhs.description).append(displayName, rhs.displayName).append(images, rhs.images).append(id, rhs.id).append(providers, rhs.providers).append(channelNumber, rhs.channelNumber).append(worksheetId, rhs.worksheetId).append(sort, rhs.sort).append(nameDeutsch, rhs.nameDeutsch).append(active, rhs.active).append(lastUpdate, rhs.lastUpdate).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(claim);
        dest.writeValue(shortname);
        dest.writeList(videoRelations);
        dest.writeValue(description);
        dest.writeValue(displayName);
        dest.writeList(images);
        dest.writeValue(id);
        dest.writeList(providers);
        dest.writeValue(channelNumber);
        dest.writeValue(worksheetId);
        dest.writeValue(sort);
        dest.writeValue(nameDeutsch);
        dest.writeValue(active);
        dest.writeValue(lastUpdate);
    }

    public int describeContents() {
        return  0;
    }

}
