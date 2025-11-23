package ru.basher.utils.wand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.basher.utils.location.V3;

@AllArgsConstructor
@Getter
@Setter
public class Wand {

	private @Nullable V3 firstPos;
	private @Nullable V3 secondPos;
	private String worldName;
	
	
	
}
