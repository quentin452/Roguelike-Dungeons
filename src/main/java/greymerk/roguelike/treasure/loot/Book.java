package greymerk.roguelike.treasure.loot;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class Book {

    private final List<String> pages;
    private String author;
    private String title;

    public Book() {
        this.pages = new ArrayList<>();
    }

    public Book(String author, String title) {
        this.pages = new ArrayList<>();
        this.author = author;
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addPage(String page) {
        this.pages.add(page);
    }

    public ItemStack get() {
        ItemStack book = new ItemStack(Items.written_book, 1);

        NBTTagList nbtPages = new NBTTagList();

        for (String page : this.pages) {
            NBTTagString nbtPage = new NBTTagString(page);
            nbtPages.appendTag(nbtPage);
        }

        book.setTagInfo("pages", nbtPages);
        book.setTagInfo("author", new NBTTagString(this.author == null ? "Anonymous" : this.author));
        book.setTagInfo("title", new NBTTagString(this.title == null ? "Book" : this.title));

        return book;
    }
}
