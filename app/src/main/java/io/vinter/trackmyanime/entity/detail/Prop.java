
package io.vinter.trackmyanime.entity.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prop {

    @SerializedName("from")
    @Expose
    private From from;
    @SerializedName("to")
    @Expose
    private To to;

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

}
