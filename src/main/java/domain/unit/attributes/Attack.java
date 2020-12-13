package domain.unit.attributes;

import lombok.Data;

@Data
public class Attack {
    AttackType attackType;
    int attackRange;
    int attackDamage;
}
