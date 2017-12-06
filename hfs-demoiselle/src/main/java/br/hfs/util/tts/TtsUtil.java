package br.hfs.util.tts;

import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;

import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

public class TtsUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("TtsUtil");

	private Synthesizer synthesizer;
	private Voice jsapiVoice;

	public void inicializarFreeTTS(String voiceName) throws TtsException {
		try {
			SynthesizerModeDesc desc = null;
			if (desc == null) {
				System.setProperty("freetts.voices",
						"com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
				// "com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory,com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
				desc = new SynthesizerModeDesc(Locale.US);
				Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
				synthesizer = Central.createSynthesizer(desc);
				synthesizer.allocate();
				synthesizer.resume();
				SynthesizerModeDesc smd = (SynthesizerModeDesc) synthesizer
						.getEngineModeDesc();
				Voice[] voices = smd.getVoices();				
				for (int i = 0; i < voices.length; i++) {
					// if (voices[i].getName().equals("alan")) {
					if (voices[i].getName().equals(voiceName)) {
						jsapiVoice = voices[i];
						break;
					}
				}
				synthesizer.getSynthesizerProperties().setVoice(jsapiVoice);				
			}
		} catch (EngineException e) {
			throw new TtsException(log, e.getMessage());
		} catch (AudioException e) {
			throw new TtsException(log, e.getMessage());
		} catch (EngineStateError e) {
			throw new TtsException(log, e.getMessage());
		} catch (PropertyVetoException e) {
			throw new TtsException(log, e.getMessage());
		}
	}

	public void falarFreeTTS(String texto) throws TtsException {
		synthesizer.speakPlainText(texto, null);
		/*
		 * synthesizer.speakPlainText(texto, new SpeakableListener() {
		 * 
		 * @Override public void wordStarted(SpeakableEvent evt) {
		 * log.info("wordStarted: " + evt.toString()); }
		 * 
		 * @Override public void topOfQueue(SpeakableEvent evt) {
		 * log.info("topOfQueue: " + evt.toString()); }
		 * 
		 * @Override public void speakableStarted(SpeakableEvent evt) {
		 * log.info("speakableStarted: " + evt.toString()); }
		 * 
		 * @Override public void speakableResumed(SpeakableEvent evt) {
		 * log.info("speakableResumed: " + evt.toString()); }
		 * 
		 * @Override public void speakablePaused(SpeakableEvent evt) {
		 * log.info("speakablePaused: " + evt.toString()); }
		 * 
		 * @Override public void speakableEnded(SpeakableEvent evt) {
		 * log.info("speakableEnded: " + evt.toString()); }
		 * 
		 * @Override public void speakableCancelled(SpeakableEvent evt) {
		 * log.info("speakableCancelled: " + evt.toString()); }
		 * 
		 * @Override public void markerReached(SpeakableEvent evt) {
		 * log.info("markerReached: " + evt.toString()); } });
		 */
		try {
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
		} catch (InterruptedException ex) {
			throw new TtsException(log, ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new TtsException(log, ex.getMessage());
		}
	}
	
	public void textoParaAudioWAV(String texto, String arquivoWAV){
	    if (jsapiVoice instanceof com.sun.speech.freetts.jsapi.FreeTTSVoice) {
	        com.sun.speech.freetts.Voice freettsVoice = 
	            ((com.sun.speech.freetts.jsapi.FreeTTSVoice) jsapiVoice).getVoice();
	        	        
	        freettsVoice.allocate();
	        
	        AudioPlayer audioPlayer = new SingleFileAudioPlayer(arquivoWAV, Type.WAVE);
	        
	        freettsVoice.setAudioPlayer(audioPlayer);
	        freettsVoice.speak(texto);
	        freettsVoice.deallocate();
	        
	        audioPlayer.close();
	    }		
	}
	
	public void terminarFreeTTS() throws TtsException {
		try {
			synthesizer.deallocate();
		} catch (EngineException e) {
			throw new TtsException(log, e.getMessage());
		} catch (EngineStateError e) {
			throw new TtsException(log, e.getMessage());
		}
	}

}
