package uz.pdp.fastfoodapp.common;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.fastfoodapp.entity.attachment.Attachment;
import uz.pdp.fastfoodapp.entity.attachment.AttachmentContent;
import uz.pdp.fastfoodapp.entity.attachment.AttachmentContentRepository;
import uz.pdp.fastfoodapp.entity.attachment.AttachmentRepository;
import uz.pdp.fastfoodapp.entity.category.Category;
import uz.pdp.fastfoodapp.entity.category.CategoryRepository;
import uz.pdp.fastfoodapp.entity.food.Food;
import uz.pdp.fastfoodapp.entity.food.FoodRepository;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final AttachmentRepository attachmentRepository;
    private final FoodRepository foodRepository;

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            createFoods();
        }
    }

    @SneakyThrows
    private void createFoods() {

        Category pizzas = categoryRepository.save(new Category("Pitsalar", "Пиццы", "Пиццалар", "Pizzas",
                null, null, null, null));

        Attachment belissimoImg = getAttachment("src/main/resources/photos/img.png");
        Attachment peperoniImg = getAttachment("src/main/resources/photos/img1.png");
        Attachment halapenoImg = getAttachment("src/main/resources/photos/img2.png");
        Attachment margaritaImg = getAttachment("src/main/resources/photos/img3.png");
        Attachment alfredoImg = getAttachment("src/main/resources/photos/img4.png");

        Food belissimo = foodRepository.save(new Food("Belissimo", "Белиссимо", "Белиссимо", "Belissimo",
                null, null, null, null, 75000.0, belissimoImg, true, LocalTime.MIN, LocalTime.MAX, pizzas, 45));

        Food peperoni = foodRepository.save(new Food("Peperoni", "Пеперони", "Пеперони", "Peperoni",
                null, null, null, null, 70000.0, peperoniImg, true, LocalTime.MIN, LocalTime.MAX, pizzas, 45));

        Food halapeno = foodRepository.save(new Food("Halapeno", "Халапеньо", "Халапеньо", "Halapeno",
                null, null, null, null, 75000.0, halapenoImg, true, LocalTime.MIN, LocalTime.MAX, pizzas, 45));

        Food margarita = foodRepository.save(new Food("Margarita", "Маргарита", "Маргарита", "Margarita",
                null, null, null, null, 67000.0, margaritaImg, true, LocalTime.MIN, LocalTime.MAX, pizzas, 45));

        Food alfredo = foodRepository.save(new Food("Alfredo", "Альфредо", "Альфредо", "Alfredo",
                null, null, null, null, 67000.0, alfredoImg, true, LocalTime.MIN, LocalTime.MAX, pizzas, 45));

    }

    @SneakyThrows
    public Attachment getAttachment(String file) throws IOException {
        String result = null;

        DataInputStream reader = new DataInputStream(new FileInputStream(file));
        int nBytesToRead = reader.available();
        if (nBytesToRead > 0) {
            byte[] bytes = new byte[nBytesToRead];
            reader.read(bytes);
            result = new String(bytes);
        }

        Attachment attachment = attachmentRepository.save(new Attachment("food", "image/png", 2048L));

        AttachmentContent attachmentContent = attachmentContentRepository.save(new AttachmentContent(attachment, reader.readAllBytes()));

        return attachment;
    }

}
