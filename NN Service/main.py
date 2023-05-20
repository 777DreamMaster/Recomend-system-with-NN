import numpy as np
from fastapi import FastAPI
from pydantic import BaseModel
import re
import pickle
import nltk
import pymorphy3
from nltk.corpus import stopwords
from tensorflow import keras



# nltk.download("stopwords")
morph = pymorphy3.MorphAnalyzer()
russian_stopwords = stopwords.words("russian")
patterns = "[0-9!#$%&'()*+,./:;<=>?@[\]^_`{|}~—\"\-]+"
# model = keras.models.load_model('lstm.h5')
# with open('tokenizer.pickle', 'rb') as handle:
#     tokenizer = pickle.load(handle)


def str_corpus(corpus):
    str_corp = ''
    for i in corpus:
        str_corp += ' ' + i
    str_corp = str_corp.strip()
    return str_corp


def lemmatize(doc):
    doc = re.sub(patterns, ' ', doc)
    doc = doc.lower().replace("ё", "е")
    tokens = []
    for token in doc.split():
        if token and token not in russian_stopwords:
            token = token.strip()
            token = morph.normal_forms(token)[0]
            tokens.append(token)
    return tokens


model = keras.models.load_model('lstm.h5')
with open('tokenizer.pickle', 'rb') as handle:
    tokenizer = pickle.load(handle)
class Message(BaseModel):
    text: str

app = FastAPI()

@app.post("/")
async def root(message: Message):
    text_comm_clear = str_corpus(lemmatize(message.text))
    text_seq = tokenizer.texts_to_sequences([text_comm_clear])
    pad_seq = keras.preprocessing.sequence.pad_sequences(text_seq, maxlen=120)
    pred = model.predict(pad_seq)[0]
    index = int(np.where(pred == np.amax(pred))[0][0] + 1)
    return index
