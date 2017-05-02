
package pokeyelp.grat.team.pokemonyelp.gson_pokemon_species;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Species {

    @SerializedName("capture_rate")
    @Expose
    private Integer captureRate;
    @SerializedName("habitat")
    @Expose
    private Habitat habitat;
    @SerializedName("color")
    @Expose
    private Color color;
    @SerializedName("forms_switchable")
    @Expose
    private Boolean formsSwitchable;
    @SerializedName("shape")
    @Expose
    private Shape shape;
    @SerializedName("names")
    @Expose
    private List<Name> names = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("egg_groups")
    @Expose
    private List<EggGroup> eggGroups = null;
    @SerializedName("base_happiness")
    @Expose
    private Integer baseHappiness;
    @SerializedName("generation")
    @Expose
    private Generation generation;
    @SerializedName("flavor_text_entries")
    @Expose
    private List<FlavorTextEntry> flavorTextEntries = null;
    @SerializedName("growth_rate")
    @Expose
    private GrowthRate growthRate;
    @SerializedName("hatch_counter")
    @Expose
    private Integer hatchCounter;
    @SerializedName("genera")
    @Expose
    private List<Genera> genera = null;
    @SerializedName("evolves_from_species")
    @Expose
    private Object evolvesFromSpecies;
    @SerializedName("form_descriptions")
    @Expose
    private List<Object> formDescriptions = null;
    @SerializedName("varieties")
    @Expose
    private List<Variety> varieties = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("evolution_chain")
    @Expose
    private EvolutionChain evolutionChain;
    @SerializedName("has_gender_differences")
    @Expose
    private Boolean hasGenderDifferences;
    @SerializedName("is_baby")
    @Expose
    private Boolean isBaby;
    @SerializedName("gender_rate")
    @Expose
    private Integer genderRate;
    @SerializedName("pal_park_encounters")
    @Expose
    private List<PalParkEncounter> palParkEncounters = null;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("pokedex_numbers")
    @Expose
    private List<PokedexNumber> pokedexNumbers = null;

    public Integer getCaptureRate() {
        return captureRate;
    }

    public void setCaptureRate(Integer captureRate) {
        this.captureRate = captureRate;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean getFormsSwitchable() {
        return formsSwitchable;
    }

    public void setFormsSwitchable(Boolean formsSwitchable) {
        this.formsSwitchable = formsSwitchable;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public List<Name> getNames() {
        return names;
    }

    public void setNames(List<Name> names) {
        this.names = names;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<EggGroup> getEggGroups() {
        return eggGroups;
    }

    public void setEggGroups(List<EggGroup> eggGroups) {
        this.eggGroups = eggGroups;
    }

    public Integer getBaseHappiness() {
        return baseHappiness;
    }

    public void setBaseHappiness(Integer baseHappiness) {
        this.baseHappiness = baseHappiness;
    }

    public Generation getGeneration() {
        return generation;
    }

    public void setGeneration(Generation generation) {
        this.generation = generation;
    }

    public List<FlavorTextEntry> getFlavorTextEntries() {
        return flavorTextEntries;
    }

    public void setFlavorTextEntries(List<FlavorTextEntry> flavorTextEntries) {
        this.flavorTextEntries = flavorTextEntries;
    }

    public GrowthRate getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(GrowthRate growthRate) {
        this.growthRate = growthRate;
    }

    public Integer getHatchCounter() {
        return hatchCounter;
    }

    public void setHatchCounter(Integer hatchCounter) {
        this.hatchCounter = hatchCounter;
    }

    public List<Genera> getGenera() {
        return genera;
    }

    public void setGenera(List<Genera> genera) {
        this.genera = genera;
    }

    public Object getEvolvesFromSpecies() {
        return evolvesFromSpecies;
    }

    public void setEvolvesFromSpecies(Object evolvesFromSpecies) {
        this.evolvesFromSpecies = evolvesFromSpecies;
    }

    public List<Object> getFormDescriptions() {
        return formDescriptions;
    }

    public void setFormDescriptions(List<Object> formDescriptions) {
        this.formDescriptions = formDescriptions;
    }

    public List<Variety> getVarieties() {
        return varieties;
    }

    public void setVarieties(List<Variety> varieties) {
        this.varieties = varieties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EvolutionChain getEvolutionChain() {
        return evolutionChain;
    }

    public void setEvolutionChain(EvolutionChain evolutionChain) {
        this.evolutionChain = evolutionChain;
    }

    public Boolean getHasGenderDifferences() {
        return hasGenderDifferences;
    }

    public void setHasGenderDifferences(Boolean hasGenderDifferences) {
        this.hasGenderDifferences = hasGenderDifferences;
    }

    public Boolean getIsBaby() {
        return isBaby;
    }

    public void setIsBaby(Boolean isBaby) {
        this.isBaby = isBaby;
    }

    public Integer getGenderRate() {
        return genderRate;
    }

    public void setGenderRate(Integer genderRate) {
        this.genderRate = genderRate;
    }

    public List<PalParkEncounter> getPalParkEncounters() {
        return palParkEncounters;
    }

    public void setPalParkEncounters(List<PalParkEncounter> palParkEncounters) {
        this.palParkEncounters = palParkEncounters;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<PokedexNumber> getPokedexNumbers() {
        return pokedexNumbers;
    }

    public void setPokedexNumbers(List<PokedexNumber> pokedexNumbers) {
        this.pokedexNumbers = pokedexNumbers;
    }

}
