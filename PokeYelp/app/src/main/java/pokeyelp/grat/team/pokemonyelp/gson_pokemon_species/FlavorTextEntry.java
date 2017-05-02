
package pokeyelp.grat.team.pokemonyelp.gson_pokemon_species;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlavorTextEntry {

    @SerializedName("version")
    @Expose
    private Version version;
    @SerializedName("flavor_text")
    @Expose
    private String flavorText;
    @SerializedName("language")
    @Expose
    private Language language;

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

}
