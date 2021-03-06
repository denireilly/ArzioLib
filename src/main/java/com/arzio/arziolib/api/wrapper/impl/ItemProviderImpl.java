package com.arzio.arziolib.api.wrapper.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.arzio.arziolib.api.util.reflection.CDClasses;
import com.arzio.arziolib.api.wrapper.CDItem;
import com.arzio.arziolib.api.wrapper.ItemProvider;

import net.minecraft.server.v1_6_R3.Item;

public class ItemProviderImpl implements ItemProvider {

	private Map<Material, CDItem> materialProviderMap = new LinkedHashMap<>();
	
	@Override
	public <T extends CDItem> T getStackAs(Class<T> clazz, ItemStack stack) {
		return stack == null ? null : this.getMaterialAs(clazz, stack.getType());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends CDItem> T getMaterialAs(Class<T> clazz, Material material) {
		CDItem item = this.getCDItemFrom(material);
		
		if (item == null) return null;
		
		if (clazz.isInstance(item)) {
			return (T) item;
		}
		
		return null;
	}
	
	@Override
	public CDItem getCDItemFrom(ItemStack stack) {
		return this.getCDItemFrom(stack.getType());
	}
	
	@Override
	public CDItem getCDItemFrom(Material material) {
		if (!isMaterialFromClass(material, CDClasses.itemCDClass.getReferencedClass())) {
			return null;
		}
		
		CDItem item = materialProviderMap.get(material);
		
		if (item == null) {

			if (isMaterialFromClass(material, CDClasses.itemGunClass.getReferencedClass())) {
				item = new GunImpl(material);
			} else if (isMaterialFromClass(material, CDClasses.itemMagazineClass.getReferencedClass())) {
				item = new AmmoImpl(material);
			} else if (isMaterialFromClass(material, CDClasses.itemClothingClass.getReferencedClass())) {
				item = new ClothingImpl(material);
			} else if (isMaterialFromClass(material, CDClasses.itemVestClass.getReferencedClass())){
				item = new VestImpl(material);
			} else if (isMaterialFromClass(material, CDClasses.itemBackpackClass.getReferencedClass())){
				item = new BackpackImpl(material);
			} else if (isMaterialFromClass(material, CDClasses.itemHatClass.getReferencedClass())){
				item = new HatImpl(material);
			} else if (isMaterialFromClass(material, CDClasses.itemGrenadeClass.getReferencedClass())){
				item = new GrenadeImpl(material);
			} else {
				item = new CDItemImpl(material);
			}
			
			materialProviderMap.put(material, item);
		}
		
		return item;
	}

	@SuppressWarnings("deprecation")
	private static boolean isMaterialFromClass(Material material, Class<?> clazz) {
		if (material == null) return false;
		return clazz.isInstance(Item.byId[material.getId()]);
	}
}
