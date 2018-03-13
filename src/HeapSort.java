import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class HeapSort
{
    int rootIndex, fatherIndex, leftKidIndex, rightKidIndex, minKidIndex, numItems;
    int heapAry[];

    HeapSort(int dataSize)
    {
        this.rootIndex = 1;
        this.fatherIndex = 0;
        this.leftKidIndex = 0;
        this.rightKidIndex = 0;
        this.minKidIndex = 0;
        this.numItems = dataSize;
        this.heapAry = new int[dataSize + 1];
        heapAry[0] = 0;
    }

    HeapSort()
    {

    }

    int countData(Scanner counter)
    {
        int count = 0;

        while (counter.hasNextInt())
        {
            count++;
            System.out.println(counter.nextInt());
        }

        return count;
    }

    void buildHeap(Scanner inFile, BufferedWriter output) throws Exception
    {
        while (inFile.hasNextInt())
        {
            int data = inFile.nextInt();
            insertOneDataItem(data);
            int kidIndex = heapAry[0];
            bubbleUp(kidIndex);
            printHeap(output);
        }

    }

	void deleteHeap(BufferedWriter output2) throws Exception
    {
        while (!isHeapEmpty())
        {
            output2.write(getRoot() + " ");
            replaceRoot();
            bubbleDown(rootIndex);
        }
    }

    void insertOneDataItem(int data)
    {
        if(isHeapFull())
        {
            return;
        }
        else
        {
            heapAry[0]++;
            heapAry[heapAry[0]] = data;
        }
    }

	int getRoot()
    {
        return this.heapAry[1];
    }

    void replaceRoot() throws Exception
    {
        heapAry[rootIndex] = heapAry[heapAry[0]];
        heapAry[0]--;
    }

	void bubbleUp(int kidIndex)
    {
        if(isRoot(kidIndex))
        {
            return;
        }
        else
        {
            fatherIndex = kidIndex / 2;

            if (heapAry[kidIndex] >= heapAry[fatherIndex])
            {
                return;
            }
            else
            {
                int temp = heapAry[kidIndex];
                heapAry[kidIndex] = heapAry[fatherIndex];
                heapAry[fatherIndex] = temp;
                bubbleUp(fatherIndex);
            }
        }


    }

    void bubbleDown(int rootIndex)
    {
        if(isLeaf(rootIndex))
        {
            return;
        }
        else
        {
            leftKidIndex = rootIndex * 2;
            rightKidIndex = rootIndex * 2 + 1;

            if (leftKidIndex > numItems && rightKidIndex > numItems)
            {
                return;
            }
            else if (rightKidIndex > numItems)
            {
                minKidIndex = leftKidIndex;
            }
            else if (leftKidIndex > numItems)
            {
                minKidIndex = rightKidIndex;
            }
            else
            {
                minKidIndex = findMinKidIndex(rootIndex);

                if (heapAry[minKidIndex] >= heapAry[rootIndex])
                {
                    return;
                }
                else
                {
                    int temp = heapAry[minKidIndex];
                    heapAry[minKidIndex] = heapAry[rootIndex];
                    heapAry[rootIndex] = temp;
                    bubbleDown(minKidIndex);
                }
            }
        }
    }

    boolean isLeaf(int index)
    {
        if(index*2 > heapAry[0] && index*2+1 > heapAry[0])
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    boolean isRoot(int index)
    {
        if(index == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    int findMinKidIndex(int fatherIndex)
    {
        leftKidIndex = fatherIndex * 2;
        rightKidIndex = fatherIndex * 2 + 1;

        if(heapAry[rightKidIndex] >= heapAry[leftKidIndex])
        {
            return leftKidIndex;
        }
        else
        {
            return rightKidIndex;
        }
    }

    boolean isHeapEmpty()
    {
        if(heapAry[0] == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

	boolean isHeapFull()
    {
        if(heapAry[0] == numItems)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    void printHeap(BufferedWriter output) throws Exception
    {
        for (int i = 0; i <= numItems; i++)
        {
            output.write(heapAry[i] + " ");
        }
        output.write("\n");
    }

    public static void main(String[] args) throws Exception
    {
        Scanner counter = new Scanner(new FileReader(args[0]));
        BufferedWriter output1 = new BufferedWriter(new FileWriter(args[1]));
        BufferedWriter output2 = new BufferedWriter(new FileWriter(args[2]));

        HeapSort obj = new HeapSort();
        int count = obj.countData(counter);
        counter.close();

        Scanner inFile  = new Scanner(new FileReader(args[0]));

        HeapSort heap = new HeapSort(count);
        heap.buildHeap(inFile, output1);
        heap.deleteHeap(output2);

        inFile.close();
        output1.close();
        output2.close();

    }

}
