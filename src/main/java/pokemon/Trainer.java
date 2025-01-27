package pokemon;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Trainer {
    String name;
    boolean isGymLeader;
    boolean gender; // true = 남자, false = 여자

    ArrayList<Pokemon> myPokemon;   // 트레이너가 소지하고있는 포켓몬
    ArrayList<Pokemon> myPokemonPc; // 트레이너의 포켓몬이 꽉차면 저장할 PC 창고

    private static final int MAX_POKEMON = 6; // 트레이너가 소지할 수 있는 최대 포켓몬 수

    public Trainer(String name, boolean isGymLeader, boolean gender) {
        this.name = name;
        this.isGymLeader = isGymLeader;
        this.gender = gender;
        this.myPokemon = new ArrayList<>(MAX_POKEMON);
        this.myPokemonPc = new ArrayList<>();
    }

    // 포켓몬 추가 메서드
    public boolean addPokemon(Pokemon pokemon) {
        if (myPokemon.size() < MAX_POKEMON) {
            myPokemon.add(pokemon);
            System.out.println(myPokemon.size() + "번째 포켓몬 추가 성공");
            return true; // 추가 성공
        } else {
            System.out.println(this.name + "은(는) 이미 최대 " + MAX_POKEMON + "마리의 포켓몬을 보유하고 있습니다.");
            System.out.println(pokemon.name + " 을(를)" + this.name + " pc에 저장합니다.");
            myPokemonPc.add(pokemon);
            return false; // 추가 실패
        }
    }

    public void tradePokemon(Trainer trainer) {
        Scanner myInput = new Scanner(System.in);
        // to String 을 오버라이딩하여 나와 상대의 포켓몬을 보여주는것이 좋아보임
        System.out.print("교환할 나의 포켓몬 번호 : ");
        int myChoiceNum = myInput.nextInt();
        System.out.print("교환할 상대의 포켓몬 번호 : ");
        int yourChoiceNum = myInput.nextInt();
        if (myChoiceNum > 0 && yourChoiceNum > 0 && myChoiceNum < myPokemon.size() + 1 && yourChoiceNum < trainer.myPokemon.size() + 1) {
            System.out.println(this.getName() + " 의 " + myPokemon.get(myChoiceNum-1).name + " 는(은) " + trainer.myPokemon.get(myChoiceNum-1).name + " 로 교환되었다!");
            Pokemon tmpPokemon = myPokemon.get(myChoiceNum - 1); // 내 포켓몬을 임시저장
            myPokemon.set(myChoiceNum - 1, trainer.myPokemon.get(yourChoiceNum - 1)); // 내 포켓몬을 상대 포켓몬으로 변경
            trainer.myPokemon.set(yourChoiceNum - 1, tmpPokemon); // 상대 포켓몬을 내 포켓몬으로 변경
        } else {
            System.out.println("잘못된 입력입니다.");
        }
    }

    // 트레이너 pc에 있는 포켓몬 출력
    public String getPcPokemon() {
        int pcnt = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("===" + this.name + " 트레이너의 pc ===");
        if (myPokemonPc.isEmpty()) {
            sb.append("\nPC가 비어있습니다.");
        } else {
            for (Pokemon pokemon : myPokemonPc) {
                sb.append("\n").append(pcnt++).append(". ").append(pokemon.name)
                        .append(", 레벨: ").append(pokemon.level)
                        .append(", HP: ").append(pokemon.hp)
                        .append(", 보유 스킬: ").append(pokemon.getSkillNames());
            }
        }
        return sb.toString();
    }

    // Trainer 정보 출력
    public String toString() {
        int pcnt = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("=== 트레이너 정보 ===")
                .append("\n트레이너 이름: ").append(this.name)
                .append("\n체육관 관장 여부: ").append(this.isGymLeader ? "예" : "아니오")
                .append("\n성별: ").append(this.gender ? "남자" : "여자")
                .append("\n보유 포켓몬: ");
        if (myPokemon.isEmpty()) {
            sb.append("없음");
        } else {
            for (Pokemon pokemon : myPokemon) {
                sb.append("\n- 포켓몬" + pcnt++ + ": ").append(pokemon.name)
                        .append(", 레벨: ").append(pokemon.level)
                        .append(", HP: ").append(pokemon.hp)
                        .append(", 보유 스킬: ").append(pokemon.getSkillNames());
            }
        }
        return sb.toString();
    }

    // 전투 시작 메서드
    public void startBattle(Trainer opponent) {
        Battle.start(this, opponent);
    }

    // 야생 포켓몬과의 전투 시작 메서드
    public void startWildBattle(Pokemon wildPokemon) {
        WildBattle.start(this, wildPokemon);
    }

    // 치료실에서 포켓몬 치료
    public void visitHealingCenter(HealingCenter healingCenter) {
        healingCenter.healPokemons(this);
    }

    // 전투에서 승리했을 때 호출되는 메서드
    public void winBattle() {
        System.out.println("\n" + this.name + "이(가) 전투에서 승리했습니다!");
        System.out.println("모든 포켓몬의 레벨이 1 상승합니다.");

        for (Pokemon pokemon : myPokemon) {
            pokemon.levelUp(); // 포켓몬 레벨 업
        }
    }

    // 다른 마을로 이동하는 메서드
    // 마을로 이동하는 메서드
    private TrainerLocation trainerLocation;
    public Trainer(TrainerLocation trainerLocation) {
        this.trainerLocation = trainerLocation;
    }

    public void moveAnotherTown(String townName, Pokemon pokemon, String skillName) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("이동하고자 하는 마을을 입력하세요 : " +
                    "현재 위치 : " + trainerLocation.getTrainerLocation() + "/n 입력 예시 :" +
                    "Kanto, Sinnoh, Hana, MoonRiseGarden"
            );
            String town = scan.nextLine();
            // 문자열을 enum 값으로 변환
            TrainerLocation.TownNames newLocation = TrainerLocation.TownNames.valueOf(town);

            // trainerLocation 업데이트
            trainerLocation.setTrainerLocation(newLocation);

            // 스킬 확인 및 이동 로직
            Skill skill = pokemon.getSkill(skillName);
            if (skill instanceof VisionSkill && ((VisionSkill) skill).canCrossOcean()) {
                System.out.println(pokemon.name + "이(가) " + skillName + "을(를) 사용하여 " + townName + "으로 이동합니다!");
            } else {
                System.out.println(pokemon.name + "은(는) " + skillName + "을(를) 사용할 수 없습니다.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 마을 이름입니다: " + townName);
        }
    }

    // Getter 메서드 추가
    public String getName() {
        return name;
    }

    public ArrayList<Pokemon> getMyPokemon() {
        return myPokemon;
    }
}