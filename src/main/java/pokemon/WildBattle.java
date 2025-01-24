package pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WildBattle {

    // 전투 시작 메서드
    public static void start(Trainer trainer, Pokemon wildPokemon) {
        System.out.println(trainer.getName() + "이(가) 야생의 " + wildPokemon.name + "와(과) 전투를 시작했습니다!");
        System.out.println("전투 시작!");

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int myIndex = 0; // 현재 사용 중인 포켓몬의 인덱스

        while (myIndex < trainer.getMyPokemon().size() && wildPokemon.hp > 0) {
            Pokemon myPokemon = trainer.getMyPokemon().get(myIndex);

            System.out.println("\n" + trainer.getName() + "의 " + myPokemon.name + " vs 야생의 " + wildPokemon.name);

            // 트레이너의 턴
            System.out.println("\n" + trainer.getName() + "의 차례!");
            System.out.println("1. 스킬 사용");
            System.out.println("2. 몬스터볼 던지기");
            System.out.println("3. 도망치기");
            System.out.print("행동을 선택하세요 (1, 2, 3): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine()); // 입력을 정수로 변환
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                continue;
            }

            switch (choice) {
                case 1:
                    // 스킬 사용
                    System.out.println(myPokemon.name + "의 스킬: " + myPokemon.getSkillNames());
                    System.out.print("사용할 스킬을 선택하세요: ");
                    String mySkill = scanner.nextLine();

                    // 스킬 조회 및 사용
                    Skill skill = myPokemon.getSkill(mySkill);
                    if (skill == null) {
                        System.out.println(myPokemon.name + "은(는) 스킬 " + mySkill + "을(를) 배우지 않았습니다.");
                        continue;
                    }

                    System.out.println("사용할 스킬: " + skill.getName());
                    skill.use("battle");

                    // 야생 포켓몬의 HP 감소
                    if (skill instanceof BattleSkill) {
                        wildPokemon.hp -= ((BattleSkill) skill).getDamage();
                    } else if (skill instanceof VisionSkill) {
                        wildPokemon.hp -= ((VisionSkill) skill).getDamage();
                    }

                    System.out.println(wildPokemon.name + "의 남은 HP: " + wildPokemon.hp);

                    // 야생 포켓몬이 쓰러졌는지 확인
                    if (wildPokemon.hp <= 0) {
                        System.out.println(wildPokemon.name + "이(가) 쓰러졌습니다!");
                        System.out.println(trainer.getName() + "의 승리!");
                        trainer.winBattle();
                        return;
                    }
                    break;

                case 2:
                    // 몬스터볼 던지기
                    System.out.println(trainer.getName() + "이(가) 몬스터볼을 던졌습니다!");
                    if (tryCatchPokemon(wildPokemon)) {
                        System.out.println("야생의 " + wildPokemon.name + "을(를) 잡았습니다!");
                        trainer.addPokemon(wildPokemon);
                        trainer.winBattle();
                        return;
                    } else {
                        System.out.println(wildPokemon.name + "은(는) 몬스터볼을 빠져나왔습니다!");
                    }
                    break;

                case 3:
                    // 도망치기
                    System.out.println(trainer.getName() + "은(는) 도망쳤습니다!");
                    return;

                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
                    continue;
            }

            // 야생 포켓몬의 턴
            if (wildPokemon.Pskill.isEmpty()) {
                System.out.println(wildPokemon.name + "은(는) 사용할 수 있는 스킬이 없습니다!");
            } else {
                System.out.println("\n야생의 " + wildPokemon.name + "의 차례!");

                // Map의 키를 List로 변환
                List<String> skillNames = new ArrayList<>(wildPokemon.Pskill.keySet());

                // 랜덤 인덱스 생성
                int randomSkillIndex = random.nextInt(skillNames.size());

                // 랜덤 스킬 선택
                String randomSkillName = skillNames.get(randomSkillIndex);
                Skill wildSkill = wildPokemon.Pskill.get(randomSkillName);

                if (wildSkill == null) {
                    System.out.println(wildPokemon.name + "의 스킬이 올바르게 초기화되지 않았습니다.");
                    return; // 전투 종료
                }

                wildPokemon.useSkill(wildSkill.getName(), "battle");

                // 내 포켓몬의 HP 감소
                if (wildSkill instanceof BattleSkill) {
                    myPokemon.hp -= ((BattleSkill) wildSkill).getDamage();
                } else if (wildSkill instanceof VisionSkill) {
                    myPokemon.hp -= ((VisionSkill) wildSkill).getDamage();
                }

                System.out.println(myPokemon.name + "의 남은 HP: " + myPokemon.hp);

                // 내 포켓몬이 쓰러졌는지 확인
                if (myPokemon.hp <= 0) {
                    System.out.println(myPokemon.name + "이(가) 쓰러졌습니다!");
                    myIndex++;
                    if (myIndex >= trainer.getMyPokemon().size()) {
                        System.out.println(trainer.getName() + "의 모든 포켓몬이 쓰러졌습니다!");
                        System.out.println("야생의 " + wildPokemon.name + "의 승리!");
                        return;
                    }
                    myPokemon = trainer.getMyPokemon().get(myIndex);
                    System.out.println(trainer.getName() + "은(는) 다음 포켓몬으로 " + myPokemon.name + "을(를) 꺼냈습니다!");
                }
            }
        }
    }

    // 몬스터볼 던지기 로직
    private static boolean tryCatchPokemon(Pokemon wildPokemon) {
        Random random = new Random();
        int catchRate = random.nextInt(100);
        return catchRate < 50; // 50% 확률로 잡기 성공
    }
}