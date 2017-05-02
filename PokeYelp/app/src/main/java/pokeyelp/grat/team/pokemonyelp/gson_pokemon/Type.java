
package pokeyelp.grat.team.pokemonyelp.gson_pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Type {

    @SerializedName("slot")
    @Expose
    private Integer slot;
    @SerializedName("type")
    @Expose
    private TypeHolder type;

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public TypeHolder getType() {
        return type;
    }

    public void setType(TypeHolder type) {
        this.type = type;
    }

}
