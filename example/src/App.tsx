import * as React from 'react';

import { StyleSheet,Alert, View, Text,TouchableOpacity } from 'react-native';
import { scanQR } from 'react-native-simple-qr-reader';

export default function App() {
  const [result, setResult] = React.useState<number | undefined>();

  // React.useEffect(() => {
  //   scanQR().then(setResult);
  // }, []);

  return (
    <View style={styles.container}>
      <TouchableOpacity 
      style={styles.button}
      onPress={() =>
        scanQR().then((r) => {
          Alert.alert("Success",r)
        })
      }
      >
<Text>Scan</Text>
      </TouchableOpacity>
      
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  button: {
    width: 20,
    length: 50,
    flex: 1,
    alignSelf: 'center',
  },
});
