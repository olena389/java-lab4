import java.util.ArrayList;
import java.util.List;

// Клас для літери
class Letter {
    private char character;

    public Letter(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}

// Клас для слова (масив літер)
class Word {
    private List<Letter> letters;

    public Word(String word) {
        this.letters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            letters.add(new Letter(c));
        }
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public String getWord() {
        StringBuilder word = new StringBuilder();
        for (Letter letter : letters) {
            word.append(letter.getCharacter());
        }
        return word.toString();
    }

    // Метод для обробки слова, видаляючи всі наступні входження першої літери
    public void processWord() {
        if (letters.isEmpty()) return;

        char firstChar = letters.get(0).getCharacter();
        for (int i = 1; i < letters.size(); i++) {
            if (letters.get(i).getCharacter() == firstChar) {
                letters.remove(i);
                i--; // Зміщення назад після видалення літери
            }
        }
    }
}

// Клас для розділових знаків
class Punctuation {
    private char punctuation;

    public Punctuation(char punctuation) {
        this.punctuation = punctuation;
    }

    public char getPunctuation() {
        return punctuation;
    }
}

// Клас для речення (масив слів і розділових знаків)
class Sentence {
    private List<Object> sentenceElements;

    public Sentence(String sentence) {
        sentenceElements = new ArrayList<>();
        String[] parts = sentence.split("\\s+");

        for (String part : parts) {
            if (part.matches("[.,!?;:]")) { // Розділові знаки
                sentenceElements.add(new Punctuation(part.charAt(0)));
            } else {
                sentenceElements.add(new Word(part));
            }
        }
    }

    public List<Object> getSentenceElements() {
        return sentenceElements;
    }

    // Метод для обробки кожного слова в реченні
    public void processSentence() {
        for (Object element : sentenceElements) {
            if (element instanceof Word) {
                ((Word) element).processWord();
            }
        }
    }

    public String getSentence() {
        StringBuilder sentence = new StringBuilder();
        for (Object element : sentenceElements) {
            if (element instanceof Word) {
                sentence.append(((Word) element).getWord()).append(" ");
            } else if (element instanceof Punctuation) {
                sentence.append(((Punctuation) element).getPunctuation()).append(" ");
            }
        }
        return sentence.toString().trim();
    }
}

// Клас для тексту (масив речень)
class Text {
    private List<Sentence> sentences;

    public Text(String text) {
        sentences = new ArrayList<>();
        // Замінити всі послідовності пробілів і табуляцій одним пробілом
        text = text.replaceAll("[\\s\t]+", " ").trim();
        String[] sentenceArray = text.split("(?<=[.!?])\\s+"); // Розділення на речення

        for (String sentence : sentenceArray) {
            sentences.add(new Sentence(sentence));
        }
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    // Метод для обробки всього тексту
    public void processText() {
        for (Sentence sentence : sentences) {
            sentence.processSentence();
        }
    }

    public String getText() {
        StringBuilder text = new StringBuilder();
        for (Sentence sentence : sentences) {
            text.append(sentence.getSentence()).append(" ");
        }
        return text.toString().trim();
    }
}

public class Main {
    public static void main(String[] args) {
        // Вхідний текст
        String inputText = "Apples  are \tabsolutely   amazing and\t apparently awesome.";

        // Створення об'єкта класу Text
        Text text = new Text(inputText);

        // Обробка тексту
        text.processText();

        // Виведення результату
        System.out.println("Input text: " + inputText);
        System.out.println("Processed text: " + text.getText());
    }
}
