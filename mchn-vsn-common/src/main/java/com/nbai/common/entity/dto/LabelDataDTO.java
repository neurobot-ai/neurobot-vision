package com.nbai.common.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 标签数据DTO
 * </p>
 *
 * @author zhenmeng
 * @since 2021-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LabelDataDTO {

	/**
	 * id
	 */
	private int id;
	
	/**
	 * 标签名
	 */
	private String name;
	
	/**
	 * 标签颜色
	 */
	private String color;
	
	/**
	 * 父域
	 */
	private String supercategory;

}
