package com.example.myapplication.content;

import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.Arrays;

public class Items {
    private static final ArrayList<Item> items = new ArrayList<>(Arrays.asList(
                new Item("6d1", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "https://res.cloudinary.com/dlu4it0gk/image/upload/v1672201096/broccoli_n8iz4j.png?fbclid=IwAR1ALOId88NTq8kYRc3_5Y38wg-vSG6nxlOo68TX3wIPtwul340pJ9ZW3sM", 12),
                new Item("6d2", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "https://res.cloudinary.com/dlu4it0gk/image/upload/v1672201096/broccoli_n8iz4j.png?fbclid=IwAR1ALOId88NTq8kYRc3_5Y38wg-vSG6nxlOo68TX3wIPtwul340pJ9ZW3sM", 12),
                new Item("6d3", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "https://res.cloudinary.com/dlu4it0gk/image/upload/v1672201096/broccoli_n8iz4j.png?fbclid=IwAR1ALOId88NTq8kYRc3_5Y38wg-vSG6nxlOo68TX3wIPtwul340pJ9ZW3sM", 12),
                new Item("6d4", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "https://res.cloudinary.com/dlu4it0gk/image/upload/v1672201096/broccoli_n8iz4j.png?fbclid=IwAR1ALOId88NTq8kYRc3_5Y38wg-vSG6nxlOo68TX3wIPtwul340pJ9ZW3sM", 12),
                new Item("6d5", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "https://res.cloudinary.com/dlu4it0gk/image/upload/v1672201096/broccoli_n8iz4j.png?fbclid=IwAR1ALOId88NTq8kYRc3_5Y38wg-vSG6nxlOo68TX3wIPtwul340pJ9ZW3sM", 12),
                new Item("6d6", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "https://res.cloudinary.com/dlu4it0gk/image/upload/v1672201096/broccoli_n8iz4j.png?fbclid=IwAR1ALOId88NTq8kYRc3_5Y38wg-vSG6nxlOo68TX3wIPtwul340pJ9ZW3sM", 12),
                new Item("6d7", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "https://res.cloudinary.com/dlu4it0gk/image/upload/v1672201096/broccoli_n8iz4j.png?fbclid=IwAR1ALOId88NTq8kYRc3_5Y38wg-vSG6nxlOo68TX3wIPtwul340pJ9ZW3sM", 12),
                new Item("6d8", "Fresh Cabbage", "good vegtable", 10000, "vegetable", "https://res.cloudinary.com/dlu4it0gk/image/upload/v1672201096/broccoli_n8iz4j.png?fbclid=IwAR1ALOId88NTq8kYRc3_5Y38wg-vSG6nxlOo68TX3wIPtwul340pJ9ZW3sM", 12)
            ));
    private Items() {
    }

    public static ArrayList<Item> getItems() {
        return items;
    }
}
