package pokemon;

public class Location {
    public void setTrainerLocation(TownNames selectedTown) {

    }

    public enum TownNames {
        신오지방("신오지방"),
        관동지방("관동지방"),
        하나지방("하나지방"),
        달맞이동산("달맞이동산");

        public final String townName;

        // 생성자
        TownNames(String townName) {
            this.townName = townName;
        }

        // 문자열 반환
        public String getTownName() {
            return townName;
        }

        // 문자열로부터 enum 값 반환
        public static TownNames fromString(String townName) {
            for (TownNames town : TownNames.values()) {
                if (town.townName.equalsIgnoreCase(townName)) {
                    return town;
                }
            }
            throw new IllegalArgumentException("Unknown town: " + townName);
        }
    }
}
