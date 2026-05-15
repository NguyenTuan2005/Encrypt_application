package model;

public interface ILanguageModel extends Observable{
    int ENGLISH_ALPHABET_SIZE = 26;
    int VIETNAM_ALPHABET_SIZE = 256;
    String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String VIETNAM_ALPHABET = "AĂÂBCĐDEÊGHIKLMNOÔƠPQRSTUƯVXYZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỡỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ";

    void setEnglish();
    void setVietnamese();
    int getAlphabetSize();
}
