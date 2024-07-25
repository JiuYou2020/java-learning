package cn.jiuyou2020.elasticsearchentry.tools;

import cn.jiuyou2020.elasticsearchentry.domain.Books;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookDataGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYXZabcdefghijklmnopqrstuvwxyz";
    private static final Random RANDOM = new Random();

    public static List<Books> generateBookData(int count) {
        ArrayList<Books> books = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Books book = new Books();
            book.setTitle(getRandomString(10));
            book.setSubtitle(getRandomString(8));
            book.setAuthor(getRandomString(12));
            book.setTranslator(getRandomString(12));
            book.setPublisher(getRandomString(15));
            book.setIsbn(getRandomIsbn());
            book.setPublicationDate(getRandomString(10));
            book.setEdition(getRandomString(5));
            book.setPrice(BigDecimal.valueOf(getRandomPrice()));
            book.setDescription(getRandomString(50));
            book.setPages(getRandomNumberInRange(50, 1000));
            books.add(book);
        }
        return books;
    }

    private static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private static String getRandomIsbn() {
        StringBuilder isbn = new StringBuilder("978");
        for (int i = 0; i < 10; i++) {
            isbn.append(RANDOM.nextInt(10));
        }
        return isbn.toString();
    }

    private static double getRandomPrice() {
        return RANDOM.nextDouble() * 100;
    }

    private static int getRandomNumberInRange(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
