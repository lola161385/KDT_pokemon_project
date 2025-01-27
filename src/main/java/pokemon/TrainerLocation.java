package pokemon;

public class TrainerLocation {
    private static String trainerLocation = "Kanto"; // 트레이너의 현재 마을위치

    public TrainerLocation(String trainerLocation) { // 다른 마을로
        this.trainerLocation = trainerLocation;
    }
    public enum TownNames{
        Sinnoh,
        Kanto,
        Hana,
        MoonRiseGarden
    }

    public String getTownName() {
        return trainerLocation;
    }
}
