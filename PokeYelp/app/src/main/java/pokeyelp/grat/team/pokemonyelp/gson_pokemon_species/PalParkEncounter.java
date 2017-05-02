
package pokeyelp.grat.team.pokemonyelp.gson_pokemon_species;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PalParkEncounter {

    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("base_score")
    @Expose
    private Integer baseScore;
    @SerializedName("area")
    @Expose
    private Area area;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(Integer baseScore) {
        this.baseScore = baseScore;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

}
