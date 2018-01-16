package cz.wake.craftcore.utils.books;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class UtilBook {

    /**
     * Generate book
     * 
     * @param p Player
     * @param slot Slot in invetory
     * @param Autor Autor that writes the book
     * @param Title Title of the book
     * @param pages Array of pages
     */
    public static void giveBook(Player p, int slot, String Autor, String Title, String... pages){
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta bm = (BookMeta)book.getItemMeta();
        bm.setAuthor(Autor);
        bm.setTitle(Title);
        bm.setPages(pages);
        book.setItemMeta(bm);
        p.getInventory().setItem(slot, book);
    }
}
