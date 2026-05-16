package model;

public interface ILanguageModel extends Observable{
    String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int ENGLISH_ALPHABET_SIZE = ENGLISH_ALPHABET.length();
    String VIETNAM_ALPHABET = "AĂÂBCĐDEÊGHIKLMNOÔƠPQRSTUƯVXYZÁÀẢÃẠẮẰẲẴẶẤẦẨẪẬÉÈẺẼẸẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌỐỒỔỖỘỚỜỞỠỢÚÙỦŨỤỨỪỬỮỰÝỲỶỸỴ";
    int VIETNAM_ALPHABET_SIZE = VIETNAM_ALPHABET.length();
    String NUMBER_VALUE = "0123456789";

    void setEnglish();
    void setVietnamese();
    int getAlphabetSize();
}
