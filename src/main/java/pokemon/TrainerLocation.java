package pokemon;

public class TrainerLocation {
    // TownNames enum 정의
    public enum TownNames {
        Sinnoh,
        Kanto,
        Hana,
        MoonRiseGarden
    }

    private TownNames trainerLocation;

    // 생성자
    public TrainerLocation() {
        this.trainerLocation = TownNames.Kanto; // 초기 위치를 Kanto로 설정
    }

    // 현재 위치 반환
    public TownNames getTrainerLocation() {
        return trainerLocation;
    }

    // 위치 변경 메서드
    public void setTrainerLocation(TownNames newLocation) {
        this.trainerLocation = newLocation;
        System.out.println("이동 완료! 현재 위치: " + newLocation);
    }
}