package pokemon;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // 치료실 생성
            HealingCenter healingCenter = new HealingCenter("태초마을 포켓몬 센터");

            // 1. 트레이너 생성
            Trainer trainer1 = new Trainer("이슬", false, true);
            Trainer trainer2 = new Trainer("지우", true, false);

            // 2. 스킬 생성
            Skill thunderbolt = new BattleSkill("백만볼트", 90, "백만볼트의 전류를 방출");
            Skill quickAttack = new BattleSkill("전광석화", 80, "빛의 속도로 적을 가격");
            Skill bodyAttack = new BattleSkill("몸통박치기", 40, "몸통으로 박치기");
            Skill firecar = new BattleSkill("화염자동차", 60, "화염자동차");
            Skill dragonBreath = new BattleSkill("용의숨결", 100, "용의 숨결을 내뿜는다");

            Skill surf = new VisionSkill("파도타기", 50, "파도를 타고 공격", "등에 업혔다 바다를 건널 수 있다.");
            Skill stoneBrake = new VisionSkill("바위깨기", 50, "바위를 깰정도의 공격", "가로막고 있는 바위를 부쉈다.");
            Skill fly = new VisionSkill("공중날기", 50, "공중으로 날아올라 공격", "등에 탑승했다");

            // 3. 포켓몬 생성
            Map<String, Skill> pikachuSkills = new HashMap<>();
            pikachuSkills.put("백만볼트", thunderbolt);
            pikachuSkills.put("전광석화", quickAttack);
            pikachuSkills.put("공중날기", fly);
            pikachuSkills.put("파도타기", surf);

            Map<String, Skill> lizardSkills = new HashMap<>();
            lizardSkills.put("용의숨결", dragonBreath);
            lizardSkills.put("공중날기", fly);

            Map<String, Skill> arseousSkills = new HashMap<>();
            arseousSkills.put("용의숨결", dragonBreath);
            arseousSkills.put("화염자동차", firecar);
            arseousSkills.put("몸통박치기", bodyAttack);
            arseousSkills.put("바위깨기", stoneBrake);

            Pokemon pikachu1 = new Pokemon("피카츄", Pokedex.PokedexData.getTypes("피카츄"), Pokedex.PokedexData.getCategory("피카츄"), pikachuSkills, 45, 450);
            Pokemon lizard1 = new Pokemon("리자드", Pokedex.PokedexData.getTypes("리자드"), Pokedex.PokedexData.getCategory("리자드"), lizardSkills, 79, 790);
            Pokemon lizard2 = new Pokemon("리자드", Pokedex.PokedexData.getTypes("리자드"), Pokedex.PokedexData.getCategory("리자드"), lizardSkills, 30, 300);
            Pokemon arseous = new Pokemon("아르세우스", Pokedex.PokedexData.getTypes("아르세우스"), Pokedex.PokedexData.getCategory("아르세우스"), arseousSkills, 100, 1000);

            // 4. 트레이너가 포켓몬 소유
            trainer1.addPokemon(pikachu1); // Trainer 클래스의 addPokemon 메서드 사용
            trainer1.addPokemon(lizard1);  // Trainer 클래스의 addPokemon 메서드 사용
            trainer2.addPokemon(lizard2);

            // 트레이너 정보 출력
            System.out.println("\n========================================");
            System.out.println(trainer1.toString());
            System.out.println(trainer2.toString());

            // PC에 저장된 포켓몬 출력
            System.out.println("\n========================================");
            System.out.println(trainer1.getPcPokemon());
            System.out.println(trainer2.getPcPokemon());

            // 포켓몬이 기술 사용 (전투 상황)
            System.out.println("\n=== 전투 상황 ===");
            pikachu1.useSkill("파도타기", "battle"); // 피카츄 기술 사용
            pikachu1.useSkill("백만볼트", "battle");
            pikachu1.useSkill("화염자동차", "battle");

            // 포켓몬이 기술 사용 (탐험 상황)
            System.out.println("\n=== 탐험 상황 ===");
            pikachu1.useSkill("파도타기", "outside"); // 피카츄 기술 사용 (탐험 중)
            pikachu1.useSkill("백만볼트", "outside"); // 피카츄 기술 사용 (탐험 중)
            pikachu1.useSkill("화염자동차", "outside");

            // 도감 출력
            System.out.println("\n========================================");
            Pokedex.PokedexData.displayAllPokemons();

            // 특정 포켓몬 검색
            System.out.println("\n=== 특정 포켓몬 검색 ===");
            String searchName = "Mew";
            Pokedex.PokedexData.displayPokemon(searchName);
            System.out.println("\n=== 특정 포켓몬 검색2 ===");
            String searchName2 = "아르세우스";
            Pokedex.PokedexData.displayPokemon(searchName2);

            // 트레이너 간 전투 시뮬레이션
            System.out.println("\n=== 트레이너 간 전투 시뮬레이션 ===");
            trainer1.startBattle(trainer2);

            // 트레이너가 치료실 방문
            System.out.println("\n=== 치료실 방문 ===");
            trainer1.visitHealingCenter(healingCenter);

            // 치료 후 포켓몬 상태 확인
            System.out.println("\n=== 치료 후 포켓몬 상태 ===");
            System.out.println(trainer1.toString());

            // 야생 포켓몬과의 전투 시작
            System.out.println("\n=== 야생 포켓몬과의 전투 ===");
            trainer1.startWildBattle(arseous);

            // 다른 마을로 이동
            System.out.println("\n=== 다른 마을로 이동 ===");
            trainer1.moveToAnotherTown(pikachu1, "파도타기");

        } catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}