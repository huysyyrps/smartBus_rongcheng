package main.smart.common.http;

public class LoadDatahandler {

	/**
	����* ��������ʱ����
	����*/
	public void onStart() {};
	/**
	����* �������ݵ���,�õ���������
	����* @param data
	����*/
	public void onLoadCaches(String data) {};
	/**
	����* �����÷������ӿڳɹ���ȡ����ʱ,�����������
	����* @param data
	����*/
	public void onSuccess(String data) {};
	/**
	����* �����÷������ӿڻ�ȡ����ʧ��ʱ,�����������
	����* @param error ����ԭ��
	����* @param message ����ԭ������
	����*/
	public void onFailure(String error, String message) {};
	/**
	����* �������ʱ����
	����*/
	public void onFinish() {};

}
