import java.util.*;
   
public class sorting {
    
        Scanner sc = new Scanner(System.in);
        public static void printArr(int arr[]){
            for(int i=0;i<arr.length;i++){
                System.out.print(arr[i]+", ");
            }
            System.out.println();
        }
        public static void BubbleSort(int arr[]){//O(n^2)
            for(int i=0;i<arr.length-1;i++)
            {
                for(int j=0;j<arr.length-i-1;j++){
                    if(arr[j]>arr[j+1]){
                        int temp=arr[j];
                        arr[j]=arr[j+1];
                        arr[j+1]=temp;
                    }
                }
            }
            printArr(arr);
        }
        public static void SelectionSort(int arr[]){//O(n^2)
            for(int i=0;i<arr.length-1;i++)
            {
                int minpos=i;
                for(int j=i+1;j<arr.length;j++)
                {
                    if(arr[j]<arr[minpos]){minpos=j;}
                }
                int temp=arr[minpos];
                arr[minpos]=arr[i];
                arr[i]=temp;
            }
            printArr(arr);
        }
//Logic is to Pick an element from an unsorted part of the array and place it in the sorted part at the correct position
        public static void InsertionSort(int arr[]){
            for(int i=1;i<arr.length;i++)//O(n^2)
            {
                int curr=arr[i],prev=i-1;
                //While loop to find out correct position to insert.
                while(prev>=0 && arr[prev]>curr)//change second condition for descending order
                {
                    arr[prev+1]=arr[prev];
                    prev--;
                }
                //inserting the elemnet at correct position
                arr[prev+1]=curr;
            }
            printArr(arr);
        }
        static int maxArr(int arr[]){
            int max=arr[0];
            for(int i=0;i<arr.length;i++){
                if(arr[i]>max)
                    max=arr[i];
            }
            return max;
        }
        public static void CountSort(int arr[]){
            int k=maxArr(arr);
            int finalArr[]=new int[arr.length];
            int countArr[]=new int[k+1];
            int posArr[]=new int[k+1];
//Assuming all numbers are positive
            for(int i=0;i<arr.length;i++){  countArr[arr[i]]++; }
            posArr[0]=countArr[0];
            for(int i=1;i<=k;i++){  
                posArr[i]=countArr[i]+posArr[i-1];
            }

            for(int i=arr.length-1;i>=0;i--){
                int m = posArr[arr[i]]-1;
                posArr[arr[i]]--;
                finalArr[m]=arr[i];
            }
            printArr(finalArr);

        }
        public static void swap(int[] arr, int a, int b) {
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
    
        public static void Merge(int[] arr, int l, int mid, int r) {
            int i = l, j = mid + 1, k = 0;
            int[] b = new int[r - l + 1];
            while (i <= mid && j <= r) {
                if (arr[i] <= arr[j]) {
                    b[k++] = arr[i++];
                } else {
                    b[k++] = arr[j++];
                }
            }
            while (i <= mid) {
                b[k++] = arr[i++];
            }
            while (j <= r) {
                b[k++] = arr[j++];
            }
            k = 0;
            for (int m = l; m <= r; m++) {
                arr[m] = b[k++];
            }
        }
    
        public static void MergeSort(int[] arr, int l, int r) {
            if (l < r) {
                int mid = (l + r) / 2;
                MergeSort(arr, l, mid);
                MergeSort(arr, mid + 1, r);
                Merge(arr, l, mid, r);
            }
        }
    
        public static int partition(int[] arr, int l, int r) {
            while (true) {
                int i = l, j = r;
                while (j >= l && arr[j] > arr[l]) {
                    j--;
                }
                while (i <= r && arr[i] < arr[r]) {
                    i++;
                }
                if (i < j) {
                    swap(arr, i, j);
                } else {
                    swap(arr, l, j);
                    return j;
                }
            }
        }
    
        public static void quicksort(int[] arr, int l, int r) {
            if (l < r) {
                int q = partition(arr, l, r);
                quicksort(arr, l, q - 1);
                quicksort(arr, q + 1, r);
            }
        }
    
        
        public static void main(String[] args) {
            int arr[]={21,12,2,34,54,67,87,90,73,85};
            //BubbleSort(arr);
            //SelectionSort(arr);
            //InsertionSort(arr);
            //Arrays.sort(arr);//O(n log n)
            //Integer a[]={21,12,2,34,54,67,87,90,73,85};
            //Arrays.sort(a,Collections.reverseOrder()); 
            //reverseOrder will work for objects and not for primitive datatypes like int, Hence we create an Integer Object a 
            //Arrays.sort(arr,0,3); //Sorts the elements from position i=0 to i=2
            //CountSort(arr);
            MergeSort(arr, 0, arr.length - 1);
            System.out.println("Sorted array after Merge Sort:");
            for (int num : arr) {
                System.out.print(num + " ");
            }
            System.out.println();

            quicksort(arr, 0, arr.length - 1);
            System.out.println("Sorted array after Quick Sort:");
            for (int num : arr) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

