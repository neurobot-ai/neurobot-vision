package com.nbai.common.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class KeyValueDTO {
	String stringkey;
	Integer integerkey;
	String value;
}
