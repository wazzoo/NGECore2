/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
 * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
 ******************************************************************************/
package resources.z.exp.buffs;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;

import resources.objects.IDelta;

import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.Persistent;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;

@Persistent(version=3)
public class Buff implements IDelta {
	
	@NotPersistent
	private SimpleBufferAllocator bufferPool = new SimpleBufferAllocator();
	private float duration;
	private String buffName;
	private long ownerId;
	private String effect1Name, effect2Name, effect3Name, effect4Name, effect5Name;
	private float effect1Value, effect2Value, effect3Value, effect4Value, effect5Value;
	private String particleEffect;
	private boolean isDebuff;
	private boolean removeOnDeath;
	private boolean isRemovableByPlayer;
	private int maxStacks;
	private boolean isPersistent;
	private boolean removeOnRespec;
	private boolean aiRemoveOnEndCombat;
	private boolean decayOnPvPDeath;
	private long startTime;
	private int totalPlayTime;
	
	public Buff(String buffName, long ownerId) {
		this.buffName = buffName;
		this.ownerId = ownerId;
		
		DatatableVisitor visitor;
		
		try {
			
			visitor = ClientFileManager.loadFile("datatables/buff/buff.iff", DatatableVisitor.class);
	
			for (int i = 0; i < visitor.getRowCount(); i++) {
			
				if (visitor.getObject(i, 0) != null) {
					
					if (((String) visitor.getObject(i, 0)).equalsIgnoreCase(buffName)) {
	
						duration = (Float) visitor.getObject(i, 6);
						effect1Name = (String) visitor.getObject(i, 7);
						effect1Value = (Float) visitor.getObject(i, 8);
						effect2Name = (String) visitor.getObject(i, 9);
						effect2Value = (Float) visitor.getObject(i, 10);
						effect3Name = (String) visitor.getObject(i, 11);
						effect3Value = (Float) visitor.getObject(i, 12);
						effect4Name = (String) visitor.getObject(i, 13);
						effect4Value = (Float) visitor.getObject(i, 14);
						effect5Name = (String) visitor.getObject(i, 15);
						effect5Value = (Float) visitor.getObject(i, 16);
						particleEffect = (String) visitor.getObject(i, 19);
						isDebuff = (Boolean) visitor.getObject(i, 22);
						removeOnDeath = (Integer) visitor.getObject(i, 25) != 0;
						isRemovableByPlayer = (Integer) visitor.getObject(i, 26) != 0;
						maxStacks = (Integer) visitor.getObject(i, 28);
						isPersistent = (Integer) visitor.getObject(i, 29) != 0;
						removeOnRespec = (Integer) visitor.getObject(i, 31) != 0;
						aiRemoveOnEndCombat = (Integer) visitor.getObject(i, 32) != 0;
						decayOnPvPDeath = (Integer) visitor.getObject(i, 33) != 0;
						
					}
				
				}
			
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public Buff() {
		
	}
	
	public byte[] getBytes() {
		IoBuffer buffer = bufferPool.allocate(28, false).order(ByteOrder.LITTLE_ENDIAN);
		if (duration > 0) {
			buffer.putInt((int) (totalPlayTime + getRemainingDuration()));		
			buffer.putInt(0);
			buffer.putInt((int) duration);
		} else {
			buffer.putInt(-1);
			buffer.putInt(0);
			buffer.putInt(-1);
		}
		buffer.putLong(ownerId);
		buffer.putInt(1); // Unknown
		buffer.flip();
		return buffer.array();
	}
	
	public float getDuration() {
		return duration;
	}
	
	public void setDuration(float duration) {
		this.duration = duration;
	}
	
	public String getBuffName() {
		return buffName;
	}
	
	public void setBuffName(String buffName) {
		this.buffName = buffName;
	}
	
	public long getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getEffect1Name() {
		return effect1Name;
	}
	
	public void setEffect1Name(String effect1Name) {
		this.effect1Name = effect1Name;
	}
	
	public String getEffect2Name() {
		return effect2Name;
	}
	
	public void setEffect2Name(String effect2Name) {
		this.effect2Name = effect2Name;
	}
	
	public String getEffect3Name() {
		return effect3Name;
	}
	
	public void setEffect3Name(String effect3Name) {
		this.effect3Name = effect3Name;
	}
	
	public String getEffect4Name() {
		return effect4Name;
	}
	
	public void setEffect4Name(String effect4Name) {
		this.effect4Name = effect4Name;
	}
	
	public String getEffect5Name() {
		return effect5Name;
	}
	
	public void setEffect5Name(String effect5Name) {
		this.effect5Name = effect5Name;
	}
	
	public float getEffect1Value() {
		return effect1Value;
	}
	
	public void setEffect1Value(float effect1Value) {
		this.effect1Value = effect1Value;
	}
	
	public float getEffect2Value() {
		return effect2Value;
	}
	
	public void setEffect2Value(float effect2Value) {
		this.effect2Value = effect2Value;
	}
	
	public float getEffect3Value() {
		return effect3Value;
	}
	
	public void setEffect3Value(float effect3Value) {
		this.effect3Value = effect3Value;
	}
	
	public float getEffect4Value() {
		return effect4Value;
	}
	
	public void setEffect4Value(float effect4Value) {
		this.effect4Value = effect4Value;
	}
	
	public float getEffect5Value() {
		return effect5Value;
	}
	
	public void setEffect5Value(float effect5Value) {
		this.effect5Value = effect5Value;
	}
	
	public String getParticleEffect() {
		return particleEffect;
	}
	
	public void setParticleEffect(String particleEffect) {
		this.particleEffect = particleEffect;
	}
	
	public boolean isDebuff() {
		return isDebuff;
	}
	
	public void setDebuff(boolean isDebuff) {
		this.isDebuff = isDebuff;
	}
	
	public boolean isRemoveOnDeath() {
		return removeOnDeath;
	}
	
	public void setRemoveOnDeath(boolean removeOnDeath) {
		this.removeOnDeath = removeOnDeath;
	}
	
	public boolean isRemovableByPlayer() {
		return isRemovableByPlayer;
	}
	
	public void setRemovableByPlayer(boolean isRemovableByPlayer) {
		this.isRemovableByPlayer = isRemovableByPlayer;
	}
	
	public int getMaxStacks() {
		return maxStacks;
	}
	
	public void setMaxStacks(int maxStacks) {
		this.maxStacks = maxStacks;
	}
	
	public boolean isPersistent() {
		return isPersistent;
	}
	
	public void setPersistent(boolean isPersistent) {
		this.isPersistent = isPersistent;
	}
	
	public boolean isRemoveOnRespec() {
		return removeOnRespec;
	}
	
	public void setRemoveOnRespec(boolean removeOnRespec) {
		this.removeOnRespec = removeOnRespec;
	}
	
	public boolean isAiRemoveOnEndCombat() {
		return aiRemoveOnEndCombat;
	}
	
	public void setAiRemoveOnEndCombat(boolean aiRemoveOnEndCombat) {
		this.aiRemoveOnEndCombat = aiRemoveOnEndCombat;
	}
	
	public boolean isDecayOnPvPDeath() {
		return decayOnPvPDeath;
	}
	
	public void setDecayOnPvPDeath(boolean decayOnPvPDeath) {
		this.decayOnPvPDeath = decayOnPvPDeath;
	}
	
	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
	}
	
	public int getRemainingDuration() {
		long currentTime = System.currentTimeMillis();
		long timeDiff = (currentTime - startTime) / 1000;
		int remaining = (int) (duration - timeDiff);
		System.out.println("Buff remaining: " + remaining);
		return remaining;
	}
	
	public int getTotalPlayTime() {
		return totalPlayTime;
	}
	
	public void setTotalPlayTime(int totalPlayTime) {
		this.totalPlayTime = totalPlayTime;
	}
	
}
