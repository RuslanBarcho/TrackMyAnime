package io.vinter.trackmyanime.entity.animelist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("animes")
    @Expose
    private List<AnimeListItem> animes = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<AnimeListItem> getAnimes() {
        return animes;
    }

    public void setAnimes(List<AnimeListItem> animes) {
        this.animes = animes;
    }

}
