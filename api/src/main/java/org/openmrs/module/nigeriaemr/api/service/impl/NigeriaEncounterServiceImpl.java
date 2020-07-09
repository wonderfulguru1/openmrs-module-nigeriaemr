package org.openmrs.module.nigeriaemr.api.service.impl;

import org.openmrs.*;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.EncounterServiceImpl;
import org.openmrs.module.nigeriaemr.api.dao.NigeriaEncounterDAO;
import org.openmrs.module.nigeriaemr.api.service.NigeriaEncounterService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
public class NigeriaEncounterServiceImpl extends EncounterServiceImpl implements NigeriaEncounterService {
	
	private NigeriaEncounterDAO dao;
	
	public void setDao(NigeriaEncounterDAO dao) {
		this.dao = dao;
	}
	
	@Override
	@Cacheable(value = "encounters")
	public List<Encounter> getEncountersByPatient(Patient patient, Date from, Date to) throws APIException {
		List<Encounter> encounters;
		encounters = dao.getEncountersByPatient(patient, from, to);
		if (encounters == null) {
			return new ArrayList<>();
		}
		return encounters;
	}
	
	@Override
	public Encounter getLastEncounterByPatient(Patient patient, Date from, Date to) throws APIException {
		Encounter encounter = null;
		try {
			encounter = dao.getLastEncounterByPatient(patient, from, to);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return encounter;
	}
	
	@Override
	@Cacheable(value = "encounters")
	public List<Encounter> getEncountersByEncounterTypeIds(Patient patient, Date fromDate, Date toDate,
	        List<Integer> encounterTypeIds) throws APIException {
		List<Encounter> encounters = null;
		try {
			encounters = dao.getEncountersByEncounterTypeIds(patient, fromDate, toDate, encounterTypeIds, false, 0);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return encounters;
	}
	
	@Override
	public Encounter getLastEncounterByEncounterTypeIds(Patient patient, Date fromDate, Date toDate,
	        List<Integer> encounterTypeIds) throws APIException {
		List<Encounter> encounters = null;
		try {
			encounters = dao.getEncountersByEncounterTypeIds(patient, fromDate, toDate, encounterTypeIds, false, 1);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return encounters == null || encounters.size() == 0 ? null : encounters.get(0);
	}
}