
package pokeyelp.grat.team.pokemonyelp.gson_pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionDetail {

    @SerializedName("version")
    @Expose
    private VersionHolder version;
    @SerializedName("rarity")
    @Expose
    private Integer rarity;

    public VersionHolder getVersion() {
        return version;
    }

    public void setVersion(VersionHolder version) {
        this.version = version;
    }

    public Integer getRarity() {
        return rarity;
    }

    public void setRarity(Integer rarity) {
        this.rarity = rarity;
    }

}
