package com.suncreate.spatialquery.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ebiz.ssi.domain.BaseDomain;

/**
 * @author Coder AutoGenerator
 * @date 2015-06-04 13:14:50
 */
public class GisCameraPositionRecord extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * ID
	 */
	private Long id;
	
	/**
	 * �˼�����
	 */
	private String rod_no;
	
	/**
	 * �˼�λ�ã�����
	 */
	private String rod_direction;
	
	/**
	 * ��λ���˼�������
	 */
	private String position_name;
	
	/**
	 * ��������ID
	 */
	private BigDecimal org_id;
	
	/**
	 * �����������
	 */
	private BigDecimal p_index;
	
	/**
	 * ��װ��ϸ��ַ
	 */
	private String address;
	
	/**
	 * ����
	 */
	private String longitude;
	
	/**
	 * γ��
	 */
	private String latitude;
	
	/**
	 * ��λ״̬:-55 �������δͨ����-5 �������δͨ����0 ���⣬ 5 ��������ˣ�10 �������࣬ 20 ���߷��裬 30 �豸��װ�� 40 ���ص��ԣ� 50 ͼ����룬 55 ���������
	 */
	private Integer position_state;
	
	/**
	 * ʩ��ͼURL
	 */
	private String img_path;
	
	/**
	 * �ֵ�
	 */
	private String street;
	
	/**
	 * �ص㵥λ
	 */
	private String key_unit;
	
	/**
	 * ��λ����
	 */
	private String unit_type;
	
	/**
	 * ��������
	 */
	private String community;
	
	/**
	 * �˼�����
	 */
	private String rod_type;
	
	/**
	 * �������
	 */
	private String across_arm_type;
	
	/**
	 * ���䰲װ��ʽ
	 */
	private String box_install_way;
	
	/**
	 * ����ͷ����
	 */
	private BigDecimal camera_num;
	
	/**
	 * ��װ������
	 */
	private String install_resp_person;
	
	/**
	 * ��װ����
	 */
	private Date install_date;
	
	/**
	 * �Ƿ�ɾ��
	 */
	private BigDecimal is_del;
	
	/**
	 * ����ֵ
	 */
	private Integer order_value;
	
	/**
	 * �����
	 */
	private BigDecimal add_id;
	
	/**
	 * ���ʱ��
	 */
	private Date add_time;
	
	/**
	 * �޸���
	 */
	private BigDecimal modify_id;
	
	/**
	 * �޸�ʱ��
	 */
	private Date modify_time;
	
	/**
	 * �������ͣ�0-�޸����ݣ�1-״̬���£�2-��λ���
	 */
	private Integer gis_opt_type;
	
	/**
	 * �����
	 */
	private BigDecimal gis_opt_user_id;
	
	/**
	 * ���ʱ��
	 */
	private Date gis_opt_time;
	
	/**
	 * ��ע
	 */
	private String gis_remark;
	
	public GisCameraPositionRecord() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRod_no() {
		return rod_no;
	}

	public void setRod_no(String rod_no) {
		this.rod_no = rod_no;
	}
	
	public String getRod_direction() {
		return rod_direction;
	}

	public void setRod_direction(String rod_direction) {
		this.rod_direction = rod_direction;
	}
	
	public String getPosition_name() {
		return position_name;
	}

	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	
	public BigDecimal getOrg_id() {
		return org_id;
	}

	public void setOrg_id(BigDecimal org_id) {
		this.org_id = org_id;
	}
	
	public BigDecimal getP_index() {
		return p_index;
	}

	public void setP_index(BigDecimal p_index) {
		this.p_index = p_index;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public Integer getPosition_state() {
		return position_state;
	}

	public void setPosition_state(Integer position_state) {
		this.position_state = position_state;
	}
	
	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getKey_unit() {
		return key_unit;
	}

	public void setKey_unit(String key_unit) {
		this.key_unit = key_unit;
	}
	
	public String getUnit_type() {
		return unit_type;
	}

	public void setUnit_type(String unit_type) {
		this.unit_type = unit_type;
	}
	
	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}
	
	public String getRod_type() {
		return rod_type;
	}

	public void setRod_type(String rod_type) {
		this.rod_type = rod_type;
	}
	
	public String getAcross_arm_type() {
		return across_arm_type;
	}

	public void setAcross_arm_type(String across_arm_type) {
		this.across_arm_type = across_arm_type;
	}
	
	public String getBox_install_way() {
		return box_install_way;
	}

	public void setBox_install_way(String box_install_way) {
		this.box_install_way = box_install_way;
	}
	
	public BigDecimal getCamera_num() {
		return camera_num;
	}

	public void setCamera_num(BigDecimal camera_num) {
		this.camera_num = camera_num;
	}
	
	public String getInstall_resp_person() {
		return install_resp_person;
	}

	public void setInstall_resp_person(String install_resp_person) {
		this.install_resp_person = install_resp_person;
	}
	
	public Date getInstall_date() {
		return install_date;
	}

	public void setInstall_date(Date install_date) {
		this.install_date = install_date;
	}
	
	public BigDecimal getIs_del() {
		return is_del;
	}

	public void setIs_del(BigDecimal is_del) {
		this.is_del = is_del;
	}
	
	public Integer getOrder_value() {
		return order_value;
	}

	public void setOrder_value(Integer order_value) {
		this.order_value = order_value;
	}
	
	public BigDecimal getAdd_id() {
		return add_id;
	}

	public void setAdd_id(BigDecimal add_id) {
		this.add_id = add_id;
	}
	
	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	
	public BigDecimal getModify_id() {
		return modify_id;
	}

	public void setModify_id(BigDecimal modify_id) {
		this.modify_id = modify_id;
	}
	
	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	
	public Integer getGis_opt_type() {
		return gis_opt_type;
	}

	public void setGis_opt_type(Integer gis_opt_type) {
		this.gis_opt_type = gis_opt_type;
	}
	
	public BigDecimal getGis_opt_user_id() {
		return gis_opt_user_id;
	}

	public void setGis_opt_user_id(BigDecimal gis_opt_user_id) {
		this.gis_opt_user_id = gis_opt_user_id;
	}
	
	public Date getGis_opt_time() {
		return gis_opt_time;
	}

	public void setGis_opt_time(Date gis_opt_time) {
		this.gis_opt_time = gis_opt_time;
	}
	
	public String getGis_remark() {
		return gis_remark;
	}

	public void setGis_remark(String gis_remark) {
		this.gis_remark = gis_remark;
	}
	
}