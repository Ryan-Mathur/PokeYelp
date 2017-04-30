
package pokeyelp.grat.team.pokemonyelp.gson_yelp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hour {

    @SerializedName("hours_type")
    @Expose
    private String hoursType;
    @SerializedName("open")
    @Expose
    private List<Open> open = null;
    @SerializedName("is_open_now")
    @Expose
    private Boolean isOpenNow;

    public String getHoursType() {
        return hoursType;
    }

    public void setHoursType(String hoursType) {
        this.hoursType = hoursType;
    }

    public List<Open> getOpen() {
        return open;
    }

    public void setOpen(List<Open> open) {
        this.open = open;
    }

    public Boolean getIsOpenNow() {
        return isOpenNow;
    }

    public void setIsOpenNow(Boolean isOpenNow) {
        this.isOpenNow = isOpenNow;
    }

}
