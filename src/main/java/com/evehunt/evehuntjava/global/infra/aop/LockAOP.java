package com.evehunt.evehuntjava.global.infra.aop;

import com.evehunt.evehuntjava.global.exception.exception.LockTimeoutException;
import com.evehunt.evehuntjava.global.infra.aop.annotation.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.Parameter;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class LockAOP{
    @Value("${lock.wait-time}")
    private long waitTime;
    @Value("${lock.lease-time}")
    private long leaseTime;
    @Value("${lock.prefix}")
    public String lockPrefix;

    private final RedissonClient redissonClient;
    private final PlatformTransactionManager transactionManager;
    @Around("@annotation(com.evehunt.evehuntjava.global.infra.aop.annotation.RedisLock)")
    private Object tryLock(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
        StringBuilder lockName = new StringBuilder(lockPrefix + " " + signature.getMethod().getAnnotation(RedisLock.class).lockName() + " ");
        Object[] parameterValues = proceedingJoinPoint.getArgs();
        Parameter[] parameters = signature.getMethod().getParameters();
         for(int i = 0; i < parameters.length; i++)
             if(parameters[i].getName().equals("id"))
             {
                 lockName.append((Long) parameterValues[i]);
                 break;
             }
        RLock rLock = redissonClient.getLock(lockName.toString());
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            System.out.println("Lock " + lockName);
            if(!rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)){
                throw new LockTimeoutException(lockName.toString());
            }
            try {
                Object ret = proceedingJoinPoint.proceed();
                transactionManager.commit(status);
                return ret;
            } catch (RuntimeException e) {
              transactionManager.rollback(status);
              throw new LockTimeoutException(lockName.toString());
            }
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                System.out.println("UnLock " + lockName);
                rLock.unlock();
            } else {
                throw new LockTimeoutException(lockName.toString());
            }
        }
    }

    public LockAOP(RedissonClient redissonClient, PlatformTransactionManager transactionManager)
    {
        this.redissonClient = redissonClient;
        this.transactionManager = transactionManager;
    }
}
