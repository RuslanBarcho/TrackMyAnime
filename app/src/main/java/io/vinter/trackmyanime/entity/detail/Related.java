
package io.vinter.trackmyanime.entity.detail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Related {

    @SerializedName("Adaptation")
    @Expose
    private List<Adaptation> adaptation = null;
    @SerializedName("Prequel")
    @Expose
    private List<Prequel> prequel = null;
    @SerializedName("Summary")
    @Expose
    private List<Summary> summary = null;
    @SerializedName("Other")
    @Expose
    private List<Other> other = null;
    @SerializedName("Sequel")
    @Expose
    private List<Sequel> sequel = null;
    @SerializedName("Alternative setting")
    @Expose
    private List<AlternativeSetting> alternativeSetting = null;

    public List<Adaptation> getAdaptation() {
        return adaptation;
    }

    public void setAdaptation(List<Adaptation> adaptation) {
        this.adaptation = adaptation;
    }

    public List<Prequel> getPrequel() {
        return prequel;
    }

    public void setPrequel(List<Prequel> prequel) {
        this.prequel = prequel;
    }

    public List<Summary> getSummary() {
        return summary;
    }

    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }

    public List<Other> getOther() {
        return other;
    }

    public void setOther(List<Other> other) {
        this.other = other;
    }

    public List<Sequel> getSequel() {
        return sequel;
    }

    public void setSequel(List<Sequel> sequel) {
        this.sequel = sequel;
    }

    public List<AlternativeSetting> getAlternativeSetting() {
        return alternativeSetting;
    }

    public void setAlternativeSetting(List<AlternativeSetting> alternativeSetting) {
        this.alternativeSetting = alternativeSetting;
    }

}
