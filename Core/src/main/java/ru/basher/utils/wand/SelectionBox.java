package ru.basher.utils.wand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.basher.utils.location.V3;

@Getter
@AllArgsConstructor
public class SelectionBox {

    private final String world;
    private final V3 minV3;
    private final V3 maxV3;

}
