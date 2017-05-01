
package pokeyelp.grat.team.pokemonyelp.gson_pokemon_species;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Name {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mLanguageName")
    @Expose
    private Language mLanguageName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguageName() {
        return mLanguageName;
    }

    public void setLanguageName(Language languageName) {
        this.mLanguageName = languageName;
    }

}
