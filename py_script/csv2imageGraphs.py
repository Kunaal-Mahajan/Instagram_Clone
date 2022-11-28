from wordsegment import load, segment
from nltk.stem import WordNetLemmatizer
from nltk.tokenize import TweetTokenizer
from sklearn import metrics
import operator

lemmatizer = WordNetLemmatizer()
tknzr = TweetTokenizer()

load()

no_split = ['eyes', 'glasses', 'instagram', 'airplane', 'aircraft', 'cheers', 'icecream', 'frenchfries','nuts', 'potatoes','octopus','profiterole','shoes','sneakers','sports']



# for j in np.arange(50):
#	G1 = nx.DiGraph()
#	img = str(j+1)+'_choose'
#	img1= str(j+1)+'_own'
#	for i in np.arange(len(H)):
#		key_list = list(set(tknzr.tokenize(H[i+1][img])+tknzr.tokenize(H[i+1][img1])))
#		keys = []
#		for key in key_list:
#			keys += segment(key)
#		keys = [lemmatizer.lemmatize(key.lower()) for key in keys if len(key)>2]
#		for key in keys:
#			G1.add_edge(H[i+1]['_id'], key, weight=h[H[i+1]['_id']]*100)
#	filename = 'img'+str(j+1)+'.net'
#	nx.write_pajek(G1,filename, encoding='UTF-8')


def ImageGraphs(data,M,h,no_split):
	for j in np.arange(M):
		G1 = nx.DiGraph()
		img = str(j+1)+'_choose'
		img1= str(j+1)+'_own'
		users = data.keys()
		for u in users:
			key_list = list(set(tknzr.tokenize(data[u][img])+tknzr.tokenize(data[u][img1])))
			key_list = [w.lower() for w in key_list]
			keys = []
			for key in key_list:
				if key in no_split:
					keys +=[key]
				else:
					keyX = segment(key)
					keyX = [lemmatizer.lemmatize(w) for w in keyX if len(w)>2]
					keys +=keyX
			keys = sorted(list(set(keys)))
			for key in keys:
				G1.add_edge(u, key, weight=h[u]*100)
		filename = 'img'+str(j+1)+'.net'
		nx.write_pajek(G1,filename, encoding='UTF-8')


def FullGraph(data,M,no_split,file_out):
	G = nx.DiGraph()
	for j in np.arange(M):
		img = str(j+1)+'_choose'
		img1= str(j+1)+'_own'
		users = data.keys()
		for u in users:
			key_list = list(set(tknzr.tokenize(data[u][img])+tknzr.tokenize(data[u][img1])))
			keys = []
			for key in key_list:
				if key in no_split:
					keys +=[key]
				else:
					keyX = segment(key)
					keyX = [lemmatizer.lemmatize(w) for w in keyX if len(w)>2]
					keys +=keyX
			keys = sorted(list(set(keys)))
			for key in keys:
				G.add_edge(u, key)
	nx.write_pajek(G,file_out, encoding='UTF-8')
	return G


def computeROC(filestart, goldfile, N, thresh_level, level):
	with open(goldfile, 'r') as fp:
		Gold = json.load(fp)
	retrieved = []
	matched = []
	gold = []
	tp = []
	fp = []
	fn = []
	for i in np.arange(N):
		filename = filestart+str(i+1)+'.net'
		gold_current = Gold[filestart+str(i+1)]
		G1 = nx.read_pajek('data/'+filename, encoding='UTF-8')
		G1 = nx.DiGraph(G1)
		[h, a] = nx.hits(G1)
		a_sort = sorted(a.items(),key = operator.itemgetter(1),reverse = True)
#		keys = [key for (key, value) in a_sort[:level]]
		keys = [key for key in a.keys() if a[key]>thresh_level]
		tp +=[key for key in keys if key in gold_current]
		fp +=[key for key in keys if key not in gold_current]
		fn +=[key for key in gold_current if key not in keys]   
		gold += gold_current
		retrieved += keys
	R = len(tp)/len(gold)
	P = len(tp)/len(retrieved)
	return R, P



		

	
