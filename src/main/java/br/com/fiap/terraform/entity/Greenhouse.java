package br.com.fiap.terraform.entity;

import br.com.fiap.terraform.enums.GreenhouseStatus;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "greenhouses")
public class Greenhouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GreenhouseStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "planet_id")
    private Planet planet;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "ph", column = @Column(name = "soil_ph", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "humidity", column = @Column(name = "soil_humidity", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "quality", column = @Column(name = "soil_quality", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "nitrogen", column = @Column(name = "soil_nitrogen", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "phosphorus", column = @Column(name = "soil_phosphorus", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "potassium", column = @Column(name = "soil_potassium", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "calcium", column = @Column(name = "soil_calcium", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "magnesium", column = @Column(name = "soil_magnesium", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "sulfur", column = @Column(name = "soil_sulfur", nullable = false, precision = 5, scale = 2))
    })
    private SoilState soil;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "oxygen", column = @Column(name = "air_oxygen", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "carbonDioxide", column = @Column(name = "air_carbon_dioxide", nullable = false, precision = 6, scale = 3)),
            @AttributeOverride(name = "humidity", column = @Column(name = "air_humidity", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "quality", column = @Column(name = "air_quality", nullable = false, precision = 5, scale = 2))
    })
    private AirState air;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "species", column = @Column(name = "plant_species", nullable = false, length = 60)),
            @AttributeOverride(name = "phase", column = @Column(name = "plant_phase", nullable = false, length = 30)),
            @AttributeOverride(name = "phaseProgress", column = @Column(name = "plant_phase_progress", nullable = false, precision = 6, scale = 2)),
            @AttributeOverride(name = "health", column = @Column(name = "plant_health", nullable = false, precision = 5, scale = 2)),
            @AttributeOverride(name = "healthStatus", column = @Column(name = "plant_health_status", nullable = false, length = 30))
    })
    private PlantState plant;

    @OneToMany(mappedBy = "greenhouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryItem> inventory = new ArrayList<>();

    public Greenhouse() {
    }

    public Greenhouse(String name, GreenhouseStatus status, Planet planet, SoilState soil, AirState air,
            PlantState plant) {
        this.name = name;
        this.status = status;
        this.planet = planet;
        this.soil = soil;
        this.air = air;
        this.plant = plant;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GreenhouseStatus getStatus() {
        return status;
    }

    public Planet getPlanet() {
        return planet;
    }

    public SoilState getSoil() {
        return soil;
    }

    public AirState getAir() {
        return air;
    }

    public PlantState getPlant() {
        return plant;
    }

    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public void rename(String name) {
        this.name = name;
    }

    public void changeStatus(GreenhouseStatus status) {
        this.status = status;
    }

    public void moveToPlanet(Planet planet) {
        this.planet = planet;
    }

    public void replaceOperationalState(SoilState soil, AirState air, PlantState plant) {
        this.soil = soil;
        this.air = air;
        this.plant = plant;
    }

    public void addInventoryItem(InventoryItem item) {
        inventory.add(item);
        item.attachTo(this);
    }
}
